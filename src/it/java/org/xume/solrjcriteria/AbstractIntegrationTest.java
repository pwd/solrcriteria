/**
 * Copyright 2010 the original author or authors.
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

package org.xume.solrjcriteria;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.core.CoreContainer;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author Johan Siebens
 */
public class AbstractIntegrationTest {

	private static SolrServer SOLR_SERVER;

	private static SolrjCriteriaFactory SOLRJ_CRITERIA_FACTORY;

	@BeforeClass
	public static void setupSolrServer() throws Exception {
		if (SOLR_SERVER == null) {
			final File solrHome = File.createTempFile("solrjcriteria", null);
			solrHome.delete();
			solrHome.mkdir();

			File solrConfDir = new File(solrHome, "conf");
			solrConfDir.mkdir();

			System.setProperty("solr.solr.home", solrHome.getAbsolutePath());

			copyResource(solrConfDir, "solrconfig.xml");
			copyResource(solrConfDir, "schema.xml");

			CoreContainer.Initializer initializer = new CoreContainer.Initializer();
			CoreContainer coreContainer = initializer.initialize();
			EmbeddedSolrServer server = new EmbeddedSolrServer(coreContainer, "");

			SOLR_SERVER = server;
			SOLRJ_CRITERIA_FACTORY = new SolrjCriteriaFactory(SOLR_SERVER);

			addCleanUpShutdownHook(solrHome);
		}
	}

	private static void addCleanUpShutdownHook(final File solrHome) {
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				FileUtils.deleteQuietly(solrHome);
			}

		});
	}

	private static void closeQuietly(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void copyResource(File solrConfDir, String resource) throws FileNotFoundException, IOException {
		InputStream in = AbstractIntegrationTest.class.getResourceAsStream("/org/xume/solrjcriteria/" + resource);
		OutputStream out = new FileOutputStream(new File(solrConfDir, resource));
		try {
			IOUtils.copy(in, out);
		}
		finally {
			closeQuietly(in);
			closeQuietly(out);
		}
	}

	@Before
	public void deleteDocuments() throws Exception {
		server().deleteByQuery("*:*");
		server().commit();
	}

	protected void addDocument(SolrInputDocument doc) throws Exception {
		server().add(doc);
		server().commit();
	}

	protected void addBean(Object bean) throws Exception {
		server().addBean(bean);
		server().commit();
	}

	protected SolrjCriteria createCriteria() {
		return SOLRJ_CRITERIA_FACTORY.createCriteria();
	}

	protected int numberOfDocuments() throws Exception {
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		SolrDocumentList results = execute(query);
		return results.size();
	}

	protected SolrServer server() {
		return SOLR_SERVER;
	}

	private SolrDocumentList execute(SolrQuery query) throws SolrServerException {
		QueryResponse rsp = server().query(query);
		SolrDocumentList results = rsp.getResults();
		return results;
	}

}
