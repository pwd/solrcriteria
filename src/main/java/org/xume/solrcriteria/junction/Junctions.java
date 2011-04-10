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

package org.xume.solrcriteria.junction;

import java.util.Arrays;
import java.util.List;

import org.xume.solrcriteria.term.Term;

/**
 * @author Johan Siebens
 */
public class Junctions {

	public static AbstractJunction and(Term... terms) {
		return and(Arrays.asList(terms));
	}

	public static AbstractJunction and(List<Term> terms) {
		return new Conjunction(terms);
	}

	public static AbstractJunction or(Term... terms) {
		return or(Arrays.asList(terms));
	}

	public static AbstractJunction or(List<Term> terms) {
		return new Disjunction(terms);
	}

}
