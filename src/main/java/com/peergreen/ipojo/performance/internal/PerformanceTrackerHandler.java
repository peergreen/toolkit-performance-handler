/**
 * Copyright 2013 Peergreen S.A.S. All rights reserved.
 * Proprietary and confidential.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.peergreen.ipojo.performance.internal;

import java.lang.reflect.Member;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.apache.felix.ipojo.ConfigurationException;
import org.apache.felix.ipojo.PrimitiveHandler;
import org.apache.felix.ipojo.annotations.Handler;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.metadata.Element;
import org.apache.felix.ipojo.parser.MethodMetadata;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

/**
 * User: guillaume
 * Date: 02/09/13
 * Time: 11:38
 */
@Handler(name = "tracker",
         namespace = "com.peergreen.performance")
public class PerformanceTrackerHandler extends PrimitiveHandler {

    public static final String TOPIC_BASE = "com/peergreen/performance/";
    public static final String FACTORY_NAME = "factory.name";
    public static final String INSTANCE_NAME = "instance.name";
    public static final String METHOD_TYPE = "method.type";
    public static final String PERFORMANCE_REPORT = "performance.report";

    @Requires
    private EventAdmin eventAdmin;

    @Override
    public void configure(final Element metadata, final Dictionary configuration) throws ConfigurationException {

        // Register interceptors for constructors
        for (MethodMetadata constructor : getPojoMetadata().getConstructors()) {
            getInstanceManager().register(constructor, new PerformanceInterceptor("constructor"));
        }

        // Select @Validate/@Invalidate methods
        Element factoryDescription = getFactory().getComponentMetadata();
        Element[] callbacks = factoryDescription.getElements("callback");
        if (callbacks != null) {
            for (Element callback : callbacks) {
                String transition = callback.getAttribute("transition");
                String method = callback.getAttribute("method");

                for (MethodMetadata methodMetadata : getPojoMetadata().getMethods(method)) {
                    getInstanceManager().register(methodMetadata, new PerformanceInterceptor(transition));
                }
            }
        }
    }

    private Map newReport(final InvocationInfo info, String type) {
        Map<String, Object> report = new HashMap<>();

        report.put(FACTORY_NAME, info.getFactory().getFactoryName());
        report.put(INSTANCE_NAME, info.getInstance().getInstanceName());
        report.put(METHOD_TYPE, type);
        report.put(PERFORMANCE_REPORT, info);

        return report;
    }

    @Override
    public void stop() {

    }

    @Override
    public void start() {

    }

    private class PerformanceInterceptor extends EmptyMethodInterceptor {

        private final String type;
        private ThreadLocal<InvocationInfo> local = new InheritableThreadLocal<>();

        public PerformanceInterceptor(final String type) {
            this.type = type;
        }

        @Override
        public void onEntry(final Object pojo, final Member method, final Object[] args) {
            if (local.get() == null) {
                // Only handle top level method invocation (do not react to re-entrant calls)
                InvocationInfo info = new InvocationInfo();
                info.setFactory(getFactory());
                info.setInstance(getInstanceManager());
                info.setMember(method);
                local.set(info);
            }
        }

        @Override
        public void onFinally(final Object pojo, final Member method) {
            InvocationInfo info = local.get();
            info.setEndTime(System.currentTimeMillis());
            local.remove();

            eventAdmin.postEvent(new Event(TOPIC_BASE + type, newReport(info, type)));
        }
    }
}
