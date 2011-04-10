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

package org.xume.solrcriteria;

import java.util.Arrays;
import java.util.List;

import org.xume.solrcriteria.junction.AbstractJunction;
import org.xume.solrcriteria.junction.Junctions;
import org.xume.solrcriteria.modified.ModifiedTerm;
import org.xume.solrcriteria.modified.ModifiedTerms;
import org.xume.solrcriteria.order.Order;
import org.xume.solrcriteria.range.Range;
import org.xume.solrcriteria.range.Ranges;
import org.xume.solrcriteria.term.Term;
import org.xume.solrcriteria.term.Terms;

/**
 * @author Johan Siebens
 */
class SolrCriteriaBuilder {

	protected final Range lt(Term term) {
		return Ranges.lt(term);
	}

	protected final Range le(Term term) {
		return Ranges.le(term);
	}

	protected final Range gt(Term term) {
		return Ranges.gt(term);
	}

	protected final Range ge(Term term) {
		return Ranges.ge(term);
	}

	protected final ModifiedTerm required(Term term) {
		return ModifiedTerms.required(term);
	}

	protected final ModifiedTerm prohibited(Term term) {
		return ModifiedTerms.prohibited(term);
	}

	protected final AbstractJunction and(Term... terms) {
		return Junctions.and(Arrays.asList(terms));
	}

	protected final AbstractJunction and(List<Term> terms) {
		return Junctions.and(terms);
	}

	protected final AbstractJunction or(Term... terms) {
		return Junctions.or(Arrays.asList(terms));
	}

	protected final AbstractJunction or(List<Term> terms) {
		return Junctions.or(terms);
	}

	protected final Term word(String value) {
		return Terms.word(value);
	}

	protected final Term phrase(String value) {
		return Terms.phrase(value);
	}

	protected final Term fielded(String field, String value) {
		return Terms.fielded(field, word(value));
	}

	protected final Term fielded(String field, Term term) {
		return Terms.fielded(field, term);
	}

	protected final Order asc(String propertyName) {
		return Order.asc(propertyName);
	}

	protected final Order desc(String propertyName) {
		return Order.desc(propertyName);
	}

}
