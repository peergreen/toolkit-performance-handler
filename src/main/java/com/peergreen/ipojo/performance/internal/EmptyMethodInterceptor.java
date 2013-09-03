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

import org.apache.felix.ipojo.MethodInterceptor;

/**
* User: guillaume
* Date: 02/09/13
* Time: 11:44
*/
class EmptyMethodInterceptor implements MethodInterceptor {
    @Override
    public void onEntry(final Object pojo, final Member method, final Object[] args) {

    }

    @Override
    public void onExit(final Object pojo, final Member method, final Object returnedObj) {

    }

    @Override
    public void onError(final Object pojo, final Member method, final Throwable throwable) {

    }

    @Override
    public void onFinally(final Object pojo, final Member method) {

    }
}
