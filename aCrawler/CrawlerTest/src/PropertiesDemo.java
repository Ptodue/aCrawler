
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


class PropertiesDemo {
	
	 private static Properties props;

	 PropertiesDemo() throws FileNotFoundException, IOException {
		
		 props = new Properties();
		 props.load(new FileInputStream("properties.properties"));
	 }
	 
	 String getProperties(String args1) {
		 
		 return props.getProperty(args1);
	 }


	public static void main(String[] args) {
		
		props = new Properties();

		/*


        try {
            // ��Xprops���Ҧ���(key, value)�t���xml(storeToXML)��txt(store)
            // ��X��Stream���|�۰�����������������A�_�h���i��X��(���O�b()���ϥ�new��)
            // storeToXML(OutputStream os, Stirng comment, String encode)
            // storeToXML(OutputStream os, Stirng comment) encode�w�]�ϥ� UTF-8
            props.storeToXML(new FileOutputStream("properties.xml"), "storeToXML");
            props.store(new FileOutputStream("properties.properties"), "store");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        props.clear();
        
        */

        try {
            System.out.println("props.getProperty(key)Ū�X(key, value)�t��");
            props.load(new FileInputStream("properties.properties"));
            System.out.println(props.getProperty("savePath", "test")); // default value test
            System.out.println(props.getProperty("keywords"));
            props.clear();
            System.out.println();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // �L�X�q properties.xml Ū�X���Ҧ�(key, value)�t��
            System.out.println("�L�Xproperties.xmlŪ�X���Ҧ�(key, value)�t��");
            props.loadFromXML(new FileInputStream("properties.xml"));
            props.setProperty("key3", "new value");
            props.list(System.out);

            // �N�s�t��g�^ properties.xml�A��y�S�X��
            props.storeToXML(new FileOutputStream("properties.xml"), "storeToXML");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

	}

}
