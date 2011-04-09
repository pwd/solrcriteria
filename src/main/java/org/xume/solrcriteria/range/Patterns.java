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
class Patterns {

	static String ge(Term term) {
		return "[ " + term.value() + " TO * ]";
	}

	static String gt(Term term) {
		return "{ " + term.value() + " TO * }";
	}

	static String le(Term term) {
		return "[ * TO " + term.value() + " ]";
	}

	static String lt(Term term) {
		return "{ * TO " + term.value() + " }";
	}

}