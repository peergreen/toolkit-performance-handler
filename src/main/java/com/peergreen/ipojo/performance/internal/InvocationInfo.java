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

import org.apache.felix.ipojo.ComponentFactory;
import org.apache.felix.ipojo.InstanceManager;

/**
 * User: guillaume
 * Date: 02/09/13
 * Time: 11:45
 */
public class InvocationInfo {
    private long beginTime = System.currentTimeMillis();
    private long endTime;
    private ComponentFactory factory;
    private InstanceManager instance;
    private Member member;

    public long getBeginTime() {
        return beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(final long endTime) {
        this.endTime = endTime;
    }

    public ComponentFactory getFactory() {
        return factory;
    }

    public void setFactory(final ComponentFactory factory) {
        this.factory = factory;
    }

    public InstanceManager getInstance() {
        return instance;
    }

    public void setInstance(final InstanceManager instance) {
        this.instance = instance;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(final Member member) {
        this.member = member;
    }
}
