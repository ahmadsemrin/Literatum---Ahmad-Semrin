package webappdesign.action;

import org.xml.sax.*;

import javax.xml.parsers.*;
import java.io.IOException;
import java.io.StringReader;

public class CheckFileValidityAction {
    public static boolean validateWithDTDUsingDOM(String xml) throws ParserConfigurationException, IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            factory.setNamespaceAware(true);

            DocumentBuilder builder = factory.newDocumentBuilder();

            builder.setErrorHandler(new ErrorHandler() {
                public void warning(SAXParseException exception) throws SAXException {
                    System.out.println("WARNING: " + exception.getMessage());
                }

                public void error(SAXParseException exception) throws SAXException {
                    System.out.println("ERROR: " + exception.getMessage());

                    throw exception;
                }

                public void fatalError(SAXParseException exception) throws SAXException {
                    System.out.println("FATAL: " + exception.getMessage());

                    throw exception;
                }
            });

            builder.parse(new InputSource(xml));

            return true;
        } catch (ParserConfigurationException | IOException e) {
            throw e;
        } catch (SAXException e) {
            e.printStackTrace();

            return false;
        }
    }
}
