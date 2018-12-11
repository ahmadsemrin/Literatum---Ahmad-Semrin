package webappdesign.action;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ActionValidateFileWithDTDUsingDOM implements IAction {
    @Override
    public Object doAction(Object object) {
        String xml = (String) object;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(true);

        try {
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
            e.printStackTrace();
        } catch (SAXException e) {
            return false;
        }

        return false;
    }
}
