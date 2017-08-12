package util.quickBuck;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import com.cloopen.rest.sdk.utils.encoder.BASE64Decoder;
import com.cloopen.rest.sdk.utils.encoder.BASE64Encoder;

import util.action.BaseAction;


public class Pkipair extends BaseAction {
	private static final long serialVersionUID = -51968499407933276L;
	public String signMsg( String signMsg) {

		String base64 = "";
		try {
			// ��Կ�ֿ�
			KeyStore ks = KeyStore.getInstance("PKCS12");//�˴�����Ҫ���޸�

			// ��ȡ��Կ�ֿ�
//			FileInputStream ksfis = new FileInputStream("e:/tester-rsa.pfx");
			
			// ��ȡ��Կ�ֿ⣨���·����
			String file = Pkipair.class.getResource("99bill-rsa.pfx").getPath().replaceAll("%20", " ");
//			System.out.println(file);
			
			FileInputStream ksfis = new FileInputStream(file);
			
			BufferedInputStream ksbufin = new BufferedInputStream(ksfis);

			char[] keyPwd = String.valueOf(fileUrlConfig.get("quickbuck_keyPwd")).toCharArray();
			//char[] keyPwd = "YaoJiaNiLOVE999Year".toCharArray();
			ks.load(ksbufin, keyPwd);
			// ����Կ�ֿ�õ�˽Կ
			PrivateKey priK = (PrivateKey) ks.getKey("test-alias", keyPwd);
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initSign(priK);
			signature.update(signMsg.getBytes("utf-8"));
			BASE64Encoder encoder = new BASE64Encoder();
			base64 = encoder.encode(signature.sign());
			
		} catch(FileNotFoundException e){
			//System.out.println("�ļ��Ҳ���");
		}catch (Exception ex) {
			ex.printStackTrace();
		}
//		System.out.println("test = "+base64);
		return base64;
	}
	public boolean enCodeByCer( String val, String msg) {
		boolean flag = false;
		try {
			//����ļ�(���·��)
			//InputStream inStream = new FileInputStream("e:/99bill[1].cert.rsa.20140803.cer");
			
			//����ļ�(���·��)
			String file = Pkipair.class.getResource("99bill.cert.rsa.20340630.cer").toURI().getPath();
			//System.out.println(file);
			FileInputStream inStream = new FileInputStream(file);
			
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
			//��ù�Կ
			PublicKey pk = cert.getPublicKey();
			//ǩ��
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initVerify(pk);
			signature.update(val.getBytes());
			//����
			BASE64Decoder decoder = new BASE64Decoder();
//			System.out.println(new String(decoder.decodeBuffer(msg)));
			flag = signature.verify(decoder.decodeBuffer(msg));
//			System.out.println(flag);
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("no");
		} 
		return flag;
	}
}
