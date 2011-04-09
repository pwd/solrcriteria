/**
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.xume.solrcriteria.range;

import org.xume.solrcriteria.terms.Term;

/**
 * @author Johan Siebens
 */
public class Ranges {

	public static Range lt(Term term) {
		return new LessThan(term);
	}

	public static Range le(Term term) {
		return new LessThanOrEqualTo(term);
	}

	public static Range gt(Term term) {
		return new GreaterThan(term);
	}

	public static Range ge(Term term) {
		return new GreaterThanOrEqualTo(term);
	}

}
