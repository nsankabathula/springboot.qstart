package io.java.springboot.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.castor.CastorMarshaller;

public class XMLConverter {

    private CastorMarshaller marshaller;
    private Unmarshaller unmarshaller;

    public Marshaller getMarshaller() {

        Map<String, String> doctypes = new HashMap<>();
        doctypes.put("SYSTEM", "flatpack.dtd");
        //marshaller.setSupportDtd(true);
        marshaller.setDoctypes(doctypes);
        //marshaller.d

        return marshaller;
    }

    public void setMarshaller(CastorMarshaller marshaller) {
        this.marshaller = marshaller;
    }

    public Unmarshaller getUnmarshaller() {
        return unmarshaller;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    public void convertFromObjectToXML(Object object, String filepath)
            throws IOException {

        FileOutputStream os = null;
        try {
            os = new FileOutputStream(filepath);

            getMarshaller().marshal(object, new StreamResult(os));
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    public Object convertFromXMLToObject(String xmlfile) throws IOException {

        FileInputStream is = null;
        try {
            is = new FileInputStream(xmlfile);
            return getUnmarshaller().unmarshal(new StreamSource(is));
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

}