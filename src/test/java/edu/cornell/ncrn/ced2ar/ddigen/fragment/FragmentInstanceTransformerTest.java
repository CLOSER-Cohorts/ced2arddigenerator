package edu.cornell.ncrn.ced2ar.ddigen.fragment;

import edu.cornell.ncrn.ced2ar.data.spss.SPSSFileException;
import edu.cornell.ncrn.ced2ar.ddigen.FileUtil;
import edu.cornell.ncrn.ced2ar.ddigen.csv.SpssCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.VariableDDIGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.Fragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.FragmentInstanceTransformer;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.LogicalProductTransformer;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.LogicalProductFactory;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.LogicalProduct;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class FragmentInstanceTransformerTest {

	private static Document fragmentInstanceDocument;

	@BeforeClass
	public static void setUp()
		throws URISyntaxException, IOException, SPSSFileException, ParserConfigurationException {
		SpssCsvGenerator spssGen = new SpssCsvGenerator();
		File file = FileUtil.getFileFromResource(FragmentInstanceTransformerTest.class, "test-file-data-types.sav");
		Document document = spssGen.getLogicalProduct(file);

		LogicalProduct logicalProduct = LogicalProductFactory.createLogicalProduct(document);
		LogicalProductTransformer logicalProductTransformer = new LogicalProductTransformer(logicalProduct);

		List<Fragment> fragmentList = logicalProductTransformer.toFragmentList();
		FragmentInstanceTransformer transformer = new FragmentInstanceTransformer(fragmentList);
		fragmentInstanceDocument = transformer.toDocument();
	}

	private Node getRepresentationNode(Document document, int index) {
		return getVariableNode(document, index).getLastChild().getLastChild();
	}

	private Node getVariableNode(Document document) {
		return getVariableNode(document, 1);
	}

	private Node getVariableNode(Document document, int index) {
		return document.getFirstChild().getChildNodes().item(index).getFirstChild();
	}

	private Node getVariableReference(Document document) {
		return getVariableScheme(document).getChildNodes().item(4);
	}

	private Node getVariableScheme(Document document) {
		return document.getFirstChild().getFirstChild().getFirstChild();
	}

	@Test
	public void testToDocument() {
		VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
		String xml = variableDDIGenerator.domToString(fragmentInstanceDocument);
		System.out.println(xml);

		Node variable = getVariableNode(fragmentInstanceDocument);

		Assert.assertEquals(15, fragmentInstanceDocument.getFirstChild().getChildNodes().getLength());
		Assert.assertEquals("r:URN", variable.getFirstChild().getNodeName());
		Assert.assertEquals("r:Agency", variable.getChildNodes().item(1).getNodeName());
		Assert.assertEquals("uk.closer", variable.getChildNodes().item(1).getTextContent());
		Assert.assertEquals("r:ID", variable.getChildNodes().item(2).getNodeName());
		Assert.assertEquals("r:Version", variable.getChildNodes().item(3).getNodeName());
		Assert.assertEquals("VariableName", variable.getChildNodes().item(4).getNodeName());
		Assert.assertEquals("r:String", variable.getChildNodes().item(4).getFirstChild().getNodeName());
		Assert.assertEquals("xml:lang", variable.getChildNodes().item(4).getFirstChild().getAttributes().item(0).getNodeName());
		Assert.assertEquals("en-GB", variable.getChildNodes().item(4).getFirstChild().getAttributes().item(0).getTextContent());
		Assert.assertEquals("TestString", variable.getChildNodes().item(4).getFirstChild().getTextContent());
		Assert.assertEquals("r:Label", variable.getChildNodes().item(5).getNodeName());
		Assert.assertEquals("xml:lang", variable.getChildNodes().item(5).getAttributes().item(0).getNodeName());
		Assert.assertEquals("en-GB", variable.getChildNodes().item(5).getAttributes().item(0).getTextContent());
		Assert.assertEquals("VariableRepresentation", variable.getChildNodes().item(6).getNodeName());
		Assert.assertEquals("VariableRole", variable.getChildNodes().item(6).getFirstChild().getNodeName());
		Assert.assertEquals("input", variable.getChildNodes().item(6).getFirstChild().getTextContent());
	}

	@Test
	public void testToDocument_DateTimeRepresentation() {


		Node representation = getRepresentationNode(fragmentInstanceDocument, 7);
		Assert.assertEquals("r:DateTimeRepresentation", representation.getNodeName());
		Assert.assertEquals("blankIsMissingValue", representation.getAttributes().item(0).getNodeName());
		Assert.assertEquals("false", representation.getAttributes().item(0).getTextContent());
		Assert.assertEquals("r:DateTypeCode", representation.getFirstChild().getNodeName());
		Assert.assertEquals("Date", representation.getFirstChild().getTextContent());
	}

	@Test
	public void testToDocument_TextRepresentation() {


		Node representation = getRepresentationNode(fragmentInstanceDocument, 1);
		Assert.assertEquals("r:TextRepresentation", representation.getNodeName());
		Assert.assertEquals("blankIsMissingValue", representation.getAttributes().item(0).getNodeName());
		Assert.assertEquals("false", representation.getAttributes().item(0).getTextContent());
		Assert.assertEquals("classificationLevel", representation.getAttributes().item(1).getNodeName());
		Assert.assertEquals("Nominal", representation.getAttributes().item(1).getTextContent());
	}

	@Test
	public void testToDocument_NumericRepresentation() {


		Node representation = getRepresentationNode(fragmentInstanceDocument, 2);
		Assert.assertEquals("r:NumericRepresentation", representation.getNodeName());
		Assert.assertEquals("blankIsMissingValue", representation.getAttributes().item(0).getNodeName());
		Assert.assertEquals("false", representation.getAttributes().item(0).getTextContent());
		Assert.assertEquals("decimalPositions", representation.getAttributes().item(1).getNodeName());
		Assert.assertEquals("4", representation.getAttributes().item(1).getTextContent());
		Assert.assertEquals("r:NumericTypeCode", representation.getFirstChild().getNodeName());
		Assert.assertEquals("BigInteger", representation.getFirstChild().getTextContent());
	}

	@Test
	public void testToDocument_VariableScheme() {


		Node variableScheme = getVariableScheme(fragmentInstanceDocument);
		Assert.assertEquals("VariableScheme", variableScheme.getNodeName());
		Assert.assertEquals("r:URN", variableScheme.getFirstChild().getNodeName());
		Assert.assertEquals("r:Agency", variableScheme.getChildNodes().item(1).getNodeName());
		Assert.assertEquals("r:ID", variableScheme.getChildNodes().item(2).getNodeName());
		Assert.assertEquals("r:Version", variableScheme.getChildNodes().item(3).getNodeName());
	}

	@Test
	public void testToDocument_VariableReference() {
		Node variableReference = getVariableReference(fragmentInstanceDocument);
		Assert.assertEquals("r:VariableReference", variableReference.getNodeName());
		Assert.assertEquals("r:URN", variableReference.getFirstChild().getNodeName());
		Assert.assertEquals("r:Agency", variableReference.getChildNodes().item(1).getNodeName());
		Assert.assertNotEquals("", variableReference.getChildNodes().item(1).getTextContent());
		Assert.assertEquals("r:ID", variableReference.getChildNodes().item(2).getNodeName());
		Assert.assertNotEquals("", variableReference.getChildNodes().item(2).getTextContent());
		Assert.assertEquals("r:Version", variableReference.getChildNodes().item(3).getNodeName());
	}
}