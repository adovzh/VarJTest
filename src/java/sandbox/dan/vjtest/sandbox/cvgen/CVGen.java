package dan.vjtest.sandbox.cvgen;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

/**
 * @author Alexander Dovzhikov
 */
public class CVGen {
    public static void main(String[] args) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("./src/xml/cvgen/cv-default.xsl"));
        Transformer transformer = factory.newTransformer(xslt);
        
        Source text = new StreamSource(new File("./src/xml/cvgen/adovzhikov-cv.xml"));
        transformer.transform(text, new StreamResult(new File("output.xml")));
    }
}
