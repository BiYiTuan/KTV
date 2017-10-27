/**
 * @Title: HttpEngine.java
 * @Prject: ktv
 * @Package: com.sz.ead.app.ktv.dataprovider.http
 * @Description: 下载引擎
 * @author: zhaoqy
 * @date: 2015-6-8 下午8:05:20
 * @version: V1.0
 */

package com.sz.ead.app.ktv.dataprovider.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.ByteBuffer;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.sz.ead.app.ktv.dataprovider.packet.inpacket.InPacket;
import com.sz.ead.app.ktv.dataprovider.packet.outpacket.OutPacket;
import com.sz.ead.app.ktv.dataprovider.packet.outpacket.OutPacket.HTTPLINK;
import com.sz.ead.app.ktv.dataprovider.packet.outpacket.OutPacket.Method;
import com.sz.ead.app.ktv.utils.Constant;
import com.sz.ead.app.ktv.utils.FlagConstant;
import com.sz.ead.app.ktv.utils.Logcat;

public class HttpEngine extends AsyncTask<Void, Void, Void>
{
	public static final String ENCODING_UTF8 = "utf-8";   //编码格式
	private static int      mInstanceCount;      //已经创建的实例数
	private final Context   mContext;            //上下文
	private final OutPacket mOutPacket;          //外发包
	private final InPacket  mInpacket;           //处理响应信息的包
	private int             mResponseCode;       //服务器返回的响应代码,200为正常,其它则存在错误
	private int             mReadBufferTotalLen; //mBuffer存放服务器返回的数据长度
	protected int           mId;                 //一次请求唯一id,
	private String          mErrorMsg;           //本地错误描述,一般情况下是exception描述
	private ByteBuffer      mBuffer;             //mBuffer存放服务器返回的数据
	SSLContext sslContext = null;
	
	protected HttpEngine(OutPacket out, InPacket in, Context context) 
	{
		if (out == null || in == null)
		{
			throw new RuntimeException("OutPacket and Inpacke must not null");
		}	
		mOutPacket = out;
		mInpacket = in;
		mContext = context;
		mInstanceCount++;
		mId = mInstanceCount;
	}
	
	public static int getmInstanceCount() 
	{
		return mInstanceCount;
	}
	
	public Context getmContext() 
	{
		return mContext;
	}

	public int getId() 
	{
		return mId;
	}
	
	/**
	 * 网络请求被取消时调用
	 * @author zhaoqy
	 * @see android.os.AsyncTask#onCancelled()
	 */
	@Override
	protected void onCancelled() 
	{
		super.onCancelled();
		mInpacket.onCancel(mOutPacket);
		HttpEngineManager.removerWhenEnd(mId);
	}

	/**
	 * 网络请求结束后调用
	 * @author zhaoqy
	 * @param result
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Void result) 
	{
		super.onPostExecute(result);
		
		if (mResponseCode == HttpURLConnection.HTTP_OK && mReadBufferTotalLen > 0)
		{
			mInpacket.onSuccessful(mBuffer, mReadBufferTotalLen);
		}
		else
		{
			mInpacket.onNetError(mResponseCode, mErrorMsg, mId, mOutPacket);
		}
		HttpEngineManager.removerWhenEnd(mId);
	}

	/**
	 * 在此间进行网络请求
	 * @author zhaoqy
	 * @param params 未使用
	 * @return
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Void doInBackground(Void... params) 
	{
		HttpURLConnection conn = null;
		HttpsURLConnection conns = null;
		
		for(int i=0;i<2;i++)
		{
			try 
			{
				if(!NetUtil.isNetConnected(mContext))
				{
					throw new Exception("network error.");
				}
				
				if(HTTPLINK.HTTP == mOutPacket.requestLink())
				{
					conn = iniConn();
				}
				else
				{
					conns = inihttpsConn();
				}
				
				if (Method.POST == mOutPacket.requestMethod()) 
				{
					OutputStream outputStream;
					if(HTTPLINK.HTTP == mOutPacket.requestLink())
					{
						outputStream = conn.getOutputStream();
					}
					else
					{
						outputStream = conns.getOutputStream();
					}
					
					if(outputStream != null) 
					{
						mOutPacket.fillData(outputStream, mContext);
						outputStream.flush();
						outputStream.close();
					}
				}
				
				if(HTTPLINK.HTTP == mOutPacket.requestLink())
				{
					readData(conn);
				}
				else
				{
					readhttpsData(conns);
				}
			} 
			catch (ProtocolException e) 
			{
				mResponseCode = HttpURLConnection.HTTP_BAD_REQUEST;
			} 
			catch (SocketTimeoutException e) 
			{
				mResponseCode = HttpURLConnection.HTTP_CLIENT_TIMEOUT;
			} 
			catch (IOException e) 
			{
				mResponseCode = HttpURLConnection.HTTP_NOT_FOUND;
			} 
			catch (Exception e) 
			{
				mResponseCode = Constant.OTHER_ERROR;
			} 
			finally 
			{
				if (conn != null) 
				{
					conn.disconnect();
				}
				
				if (conns != null)
				{
					conns.disconnect();
				}
			}
			try 
			{
				Logcat.i(FlagConstant.TAG," response code: " + mResponseCode);
			}
			catch (Exception e) 
			{
			}
			
			if (mResponseCode == HttpURLConnection.HTTP_OK && (mReadBufferTotalLen > 0))
			{
				break;
			}
			else
			{
				Constant.changeEPGHost();
			}
		}
		
		return null;
	}

	/**
	 * 初始化网络连接
	 * @author zhaoqy
	 * @return 初始化好的Http连接句柄
	 * @throws ProtocolException
	 * @throws IOException
	 */
	private HttpURLConnection iniConn() throws ProtocolException, IOException 
	{
		HttpURLConnection httpConn = null;
		URL url = mOutPacket.serviceURL(mContext);
		httpConn = (HttpURLConnection) url.openConnection();  //不使用代理
		httpConn.setConnectTimeout(mOutPacket.getTimeout()); //设置超时
		httpConn.setReadTimeout(mOutPacket.getTimeout());
		
		Method method = mOutPacket.requestMethod();          //设置请求参数
		if (Method.POST == method) 
		{
			httpConn.setDoOutput(true);        //使用URL连接进行输出
			httpConn.setDoInput(true);         //使用URL连接进行输入
			httpConn.setUseCaches(false);      //忽略缓存
			httpConn.setRequestMethod("POST"); //设置URL请求方法
		} 
		else 
		{
			httpConn.setRequestMethod("GET");  //设置URL请求方法
		}
		
		httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); //输出键值对
		httpConn.setRequestProperty("Connection", "Keep-Alive");                          //维持长连接
		httpConn.setRequestProperty("Charset", ENCODING_UTF8);
		httpConn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.2.149.27 Safari/525.13");
		httpConn.setRequestProperty("Accept","*/*");
		httpConn.setRequestProperty("Accept-Encoding","identity");
		mOutPacket.setRequestProperty(httpConn);
		return httpConn;
	}
	
	/**
	 * 初始化网络连接
	 * @author 李英英 
	 * @return 初始化好的Https连接句柄
	 * @throws ProtocolException
	 * @throws IOException
	 */
	private HttpsURLConnection inihttpsConn() throws ProtocolException, IOException 
	{
		HttpsURLConnection httpConn = null;
		HttpsTrustHosts(mContext); //证书验证
		URL url = mOutPacket.serviceURL(mContext);
		httpConn = (HttpsURLConnection) url.openConnection();
		httpConn.setConnectTimeout(mOutPacket.getTimeout());
		httpConn.setReadTimeout(mOutPacket.getTimeout());
		
		// 设置请求参数
		Method method = mOutPacket.requestMethod();
		if (Method.POST == method) 
		{
			httpConn.setDoOutput(true);        //使用 URL 连接进行输出
			httpConn.setDoInput(true);         //使用 URL 连接进行输入
			httpConn.setUseCaches(false);      //忽略缓存
			httpConn.setRequestMethod("POST"); //设置URL请求方法
		} 
		else 
		{
			httpConn.setRequestMethod("GET");  //设置URL请求方法
		}
		
		httpConn.setSSLSocketFactory(sslContext.getSocketFactory());                      //https 验证
		httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); //输出键值对
		httpConn.setRequestProperty("Connection", "Keep-Alive");                          // 维持长连接
		httpConn.setRequestProperty("Charset", ENCODING_UTF8);
		httpConn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.2.149.27 Safari/525.13");
		httpConn.setRequestProperty("Accept","*/*");
		httpConn.setRequestProperty("Accept-Encoding","identity");
		mOutPacket.setRequestProperty(httpConn);
		return httpConn;
	}

	/**
	 * 读取服务器返回数据
	 * @param conn Http连接请求
	 * @throws SocketTimeoutException
	 * @throws IOException
	 */
	private void readData(HttpURLConnection conn) throws SocketTimeoutException, IOException 
	{
		InputStream inputStream = conn.getInputStream();
		byte[] buffer = new byte[1024 * 10];
		int responseContentLength = conn.getContentLength();
		if (responseContentLength == 0) 
		{
			mResponseCode = HttpURLConnection.HTTP_NO_CONTENT;
		} 
		else 
		{
			mResponseCode = conn.getResponseCode();
			if (mResponseCode == HttpURLConnection.HTTP_OK) 
			{
				boolean useCache = mInpacket.useCache();
				int len = 0;
				mReadBufferTotalLen = 0;
				String encoding = getEncoding(conn);
				
				if (useCache)   //循环读取数据
				{
					while ((len = inputStream.read(buffer)) != -1) 
					{
						putByteToBufer(buffer, 0, len);
						mReadBufferTotalLen += len;
					}
				} 
				else 
				{
					mBuffer = ByteBuffer.wrap(buffer);
					while ((len = inputStream.read(buffer)) != -1) 
					{
						mBuffer.clear();
						mBuffer.put(buffer, 0, len);
						mReadBufferTotalLen += len;
						mInpacket.httpResponse(mBuffer, encoding, Boolean.FALSE, Boolean.FALSE, mId, mOutPacket);
					}
				}
				mInpacket.httpResponse(mBuffer, encoding, Boolean.FALSE, Boolean.TRUE, mId, mOutPacket);
			}
		}
		if(inputStream != null) 
		{
			inputStream.close();
		}
	}
	
	/**
	 * @Title: readhttpsData
	 * @Description: conn Https连接请求
	 * @param conn
	 * @throws SocketTimeoutException
	 * @throws IOException
	 * @return: void
	 */
	private void readhttpsData(HttpsURLConnection conn) throws SocketTimeoutException, IOException 
	{
		InputStream inputStream = conn.getInputStream();
		byte[] buffer = new byte[1024 * 10];
		int responseContentLength = conn.getContentLength();
		if (responseContentLength == 0) 
		{
			mResponseCode = HttpURLConnection.HTTP_NO_CONTENT;
		} 
		else 
		{
			mResponseCode = conn.getResponseCode();
			if (mResponseCode == HttpURLConnection.HTTP_OK) 
			{
				boolean useCache = mInpacket.useCache();
				int len = 0;
				mReadBufferTotalLen = 0;
				String encoding = getEncoding(conn);
				// 循环读取数据
				if (useCache) 
				{
					while ((len = inputStream.read(buffer)) != -1) 
					{
						putByteToBufer(buffer, 0, len);
						mReadBufferTotalLen += len;
					}
				} 
				else 
				{
					mBuffer = ByteBuffer.wrap(buffer);
					while ((len = inputStream.read(buffer)) != -1) 
					{
						mBuffer.clear();
						mBuffer.put(buffer, 0, len);
						mReadBufferTotalLen += len;
						mInpacket.httpResponse(mBuffer, encoding, Boolean.FALSE, Boolean.FALSE, mId, mOutPacket);
					}
				}
				mInpacket.httpResponse(mBuffer, encoding, Boolean.FALSE, Boolean.TRUE, mId, mOutPacket);
			}
		}
		if(inputStream != null) 
		{
			inputStream.close();
		}
	}
	
	/**
	 * @Title: HttpsTrustHosts
	 * @Description: https 证书验证
	 * @param mcontext
	 * @return: void
	 */
	private void HttpsTrustHosts(Context mcontext)
	{
		try 
		{
			sslContext = SSLContext.getInstance("TLS");
			KeyManagerFactory keyManager = KeyManagerFactory.getInstance("X509");
			sslContext.init(keyManager.getKeyManagers(), new TmArray[] { new TmArray() }, null);
			X509HostnameVerifier hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
			HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
			//HttpsURLConnection.setDefaultHostnameVerifier(hnv); 
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	/**
	 * 将byte 存入ByteBuffer中,当ByteBuffer剩余空间不足时,会重新新建并扩大空间
	 * @param data 要存放的数据
	 * @param start 要存放的数据的数组起始位置
	 * @param len 要存放的数据的长度
	 */
	private void putByteToBufer(byte[] data, int start, int len) 
	{
		if (mBuffer == null) 
		{
			mBuffer = ByteBuffer.allocate(len << 2);
		} 
		else if (mBuffer.remaining() < len) 
		{
			ByteBuffer tmpBufer = ByteBuffer.allocate(mBuffer.capacity() + (len << 2));
			tmpBufer.put(mBuffer.array(), 0, mBuffer.position());
			mBuffer = null;
			mBuffer = tmpBufer;
		}
		mBuffer.put(data, start, len);
	}

	private String getEncoding(HttpURLConnection httpConn) 
	{
		String result = ENCODING_UTF8;
		if (httpConn != null) 
		{
			String contentType = httpConn.getHeaderField("Content-Type");
			if (!TextUtils.isEmpty(contentType)) 
			{
				int index = contentType.indexOf("=");
				if (index >= 0 && contentType.length() > index + 1) 
				{
					result = contentType.substring(index + 1);
				}
			}
		}
		return result;
	}
}
