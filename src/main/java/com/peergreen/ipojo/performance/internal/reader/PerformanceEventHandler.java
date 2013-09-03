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

package com.peergreen.ipojo.performance.internal.reader;

import static java.lang.String.format;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import com.peergreen.ipojo.performance.internal.InvocationInfo;
import com.peergreen.ipojo.performance.internal.PerformanceTrackerHandler;

/**
 * User: guillaume
 * Date: 02/09/13
 * Time: 12:25
 */
public class PerformanceEventHandler implements BundleActivator, EventHandler {

    private File report;

    @Override
    public synchronized void handleEvent(final Event event) {
        InvocationInfo info = (InvocationInfo) event.getProperty(PerformanceTrackerHandler.PERFORMANCE_REPORT);
        try(
                FileOutputStream fos = new FileOutputStream(report, true)
        ) {
            fos.write(format("%d;%s;%s;%s;%s;%s;%d;%d;%d%n",
                             info.getFactory().getBundleContext().getBundle().getBundleId(),
                             info.getFactory().getFactoryName(),
                             info.getInstance().getInstanceName(),
                             info.getFactory().getClassName(),
                             info.getMember().getName() + "(...)",
                             event.getProperty(PerformanceTrackerHandler.METHOD_TYPE),
                             info.getBeginTime(),
                             info.getEndTime(),
                             info.getEndTime() - info.getBeginTime()).getBytes());
            fos.flush();
        } catch (IOException e) {
            // Ignore
            System.err.println("Failed to write report to disk.");
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void start(final BundleContext context) throws Exception {

        String tmpdir = System.getProperty("java.io.tmpdir");
        report = new File(tmpdir, String.valueOf(new Date().getTime()) + "-ipojo-startup-performance.csv");
        report.getParentFile().mkdirs();

        try(
                FileOutputStream fos = new FileOutputStream(report, true)
        ) {
            fos.write(format("#%s;%s;%s;%s;%s;%s;%s;%s;%s%n",
                             "factory-bundle-id",
                             "factory-name",
                             "instance-name",
                             "class-name",
                             "method-name",
                             "method-type",
                             "begin",
                             "end",
                             "elapsed").getBytes());
            fos.flush();
        } catch (IOException e) {
            // Ignore
            System.err.println("Failed to write report header to disk.");
            e.printStackTrace(System.err);
        }

        System.out.printf("Performances report available in %s%n", report);

        Dictionary<String, String> p = new Hashtable<>();
        p.put(EventConstants.EVENT_TOPIC, PerformanceTrackerHandler.TOPIC_BASE + "*");
        context.registerService(EventHandler.class, this, p);
    }

    @Override
    public void stop(final BundleContext context) throws Exception {
    }
}
