
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
            // 輸出props中所有的(key, value)配對到xml(storeToXML)及txt(store)
            // 輸出後Stream不會自動關閉必須手動關閉，否則有可能出錯(不是在()中使用new時)
            // storeToXML(OutputStream os, Stirng comment, String encode)
            // storeToXML(OutputStream os, Stirng comment) encode預設使用 UTF-8
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
            System.out.println("props.getProperty(key)讀出(key, value)配對");
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
            // 印出從 properties.xml 讀出的所有(key, value)配對
            System.out.println("印出properties.xml讀出的所有(key, value)配對");
            props.loadFromXML(new FileInputStream("properties.xml"));
            props.setProperty("key3", "new value");
            props.list(System.out);

            // 將新配對寫回 properties.xml，串流沒出錯
            props.storeToXML(new FileOutputStream("properties.xml"), "storeToXML");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

	}

}
