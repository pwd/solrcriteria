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

import java.util.Iterator;
import java.util.List;

import org.xume.solrcriteria.term.Term;

/**
 * @author Johan Siebens
 */
public abstract class AbstractJunction implements Junction {

	private List<Term> terms;

	private String operator;

	public AbstractJunction(String operator, List<Term> terms) {
		this.terms = terms;
		this.operator = operator;
	}

	@Override
	public String value() {
		if (terms == null || terms.isEmpty()) {
			return "";
		}
		else if (terms.size() == 1) {
			return terms.get(0).value();
		}
		else {
			StringBuilder result = new StringBuilder("(");

			for (Iterator<Term> iterator = terms.iterator(); iterator.hasNext();) {
				Term term = iterator.next();
				result.append(term.value());

				if (iterator.hasNext()) {
					result.append(" " + operator + " ");
				}
			}

			result.append(")");

			return result.toString();
		}
	}

}
