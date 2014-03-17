package reader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javassist.bytecode.ByteArray;

import org.apache.commons.io.FileUtils;
import org.msgpack.MessagePack;
import org.msgpack.template.Template;
import org.msgpack.template.Templates;
import org.msgpack.type.ArrayValue;
import org.msgpack.type.MapValue;
import org.msgpack.type.Value;
import org.msgpack.type.ValueType;
import org.msgpack.unpacker.Unpacker;
import org.msgpack.unpacker.UnpackerIterator;
import static org.msgpack.template.Templates.TByteArray;
import static org.msgpack.template.Templates.TString;
import static org.msgpack.template.Templates.tMap;

public class Reader {
	
	public void testRead(String fileLoc) {
		File file = new File(fileLoc);
		MessagePack msgpack = new MessagePack();
		byte[] bytes = null;
		try {
			bytes = FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		Unpacker unpacker = msgpack.createUnpacker(in);
		
		try {
			System.err.println(unpacker.getNextType());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		StringBuilder sb = new StringBuilder();
		try {
			Value v = unpacker.readValue();
			MapValue mv =  v.asMapValue();
			for(Value key : mv.keySet()) {
				ArrayValue aKey = (ArrayValue) key;
				String tit = aKey.get(1).toString().replace("\"", "");
				String bod = "body";
				String title = "title";
				String lang = "language";
				//System.out.println(tit);
				if(tit.equals(bod) || tit.equals(title) || tit.equals(lang)) {
					System.out.println("found");
					System.out.println(mv.get(key).toString());
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.err.println(sb.toString());
		try {
			PrintWriter writer = new PrintWriter("/home/jack/Documents/output.txt","UTF-8");
			writer.write(sb.toString());
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		Reader reader = new Reader();
		reader.testRead("/home/jack/Documents/Projects/Lumi/unzippedDocs/013b69fb266327fe6a528d79b2b3d5c2");
		
	}

}
