package httpclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.util.Calendar;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HttpClient {
    public static String getTime() {
        Calendar c = Calendar.getInstance();
        String rf = "%s-%s-%s %s:%s:%s.%s";
        int Year = c.get(Calendar.YEAR);
        int Month = c.get(Calendar.MONTH) + 1;
        String sMonth = String.format("%s%d", Month > 9 ? "" : "0", Month);
        int Day = c.get(Calendar.DAY_OF_MONTH);
        String sDay = String.format("%s%d", Day > 9 ? "" : "0", Day);
        int Hour = c.get(Calendar.HOUR_OF_DAY);
        String sHour = String.format("%s%d", Hour > 9 ? "" : "0", Hour);
        int Min = c.get(Calendar.MINUTE);
        String sMin = String.format("%s%d", Min > 9 ? "" : "0", Min);
        int Sec = c.get(Calendar.SECOND);
        String sSec = String.format("%s%d", Sec > 9 ? "" : "0", Sec);
        int MS = c.get(Calendar.MILLISECOND);
        String sMS = String.format("%s%d", MS > 99 ? "" : MS > 9 ? "0" : "00", MS);
        return String.format(rf, Year, sMonth, sDay, sHour, sMin, sSec, sMS);
    }

    public static final String Head_Accept = "Accept";

    public static final String Head_Cookie = "cookie";

    public static final String Head_Referer = "referer";

    public static final String Head_UserAgent = "User-Agent";

    public static final String Head_AcceptLanguage = "accept-language";

    public static final String UserAgentIE6 = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; .NET CLR 1.1.4322)";

    public static final String UserAgentIE7 = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; .NET CLR 2.0.50727)";

    public static final String UserAgentOpera9 = "Opera/9.02 (Windows NT 5.2; U; zh-cn)";

    private String mReffer = "";

    private String mAgent = "Mozilla/4.0 (ice-plant; imdeep; HttpClient 2.0)";

    private String mAccept = "*/*;";

    private String mAcceptLanguage = "zh-cn";

    private int mLen = -1;

    private String httpEncode = "";

    private Proxy proxy = null;

    private Vector<String> Heads = new Vector<String>();

    private int connTimeout = 2000;

    public void setProxy(Proxy vProxy) {
        this.proxy = vProxy;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public void setConnTimeOut(int timeout) {
        this.connTimeout = timeout;
    }

    public void setProxy(Proxy.Type type, String IP, int Port) {
        SocketAddress sa = new InetSocketAddress(IP, Port);
        proxy = new Proxy(type, sa);
    }

    public void setEncode(String Encode) {
        if (Encode.toLowerCase().matches("gb2312")) {
            this.httpEncode = "gbk";
        } else {
            this.httpEncode = Encode;
        }
    }

    public void addHead(String key, String value) {
        if (key == null) {
            key = "";
        }
        if (value == null) {
            value = "";
        }
        Heads.add(key + ": " + value);
    }

    public String HeadKey(int index) {
        if (index < 0) {
            return "";
        }
        if (index >= Heads.size()) {
            return "";
        }
        return Heads.get(index).split(":")[0];
    }

    public String HeadValue(int index) {
        if (index < 0) {
            return "";
        }
        if (index >= Heads.size()) {
            return "";
        }
        String[] hs = Heads.get(index).split(":");
        String v = hs[1];
        for (int i = 2; i < hs.length; i++) {
            v = String.format("%s:%s", v, hs[i]);
        }
        return v;
    }

    public void setContentLength(int len) {
        this.mLen = len;
    }

    public void setReffer(String Reffer) {
        this.mReffer = Reffer;
    }

    public void setAgent(String Agent) {
        this.mAgent = Agent;
    }

    public void setAccept(String Accept) {
        this.mAccept = Accept;
    }

    private void setHeads(HttpURLConnection huc) {
        huc.setRequestProperty("User-Agent", this.mAgent);
        if (this.mReffer.length() > 0) {
            huc.setRequestProperty("referer", this.mReffer);
        }
        huc.setRequestProperty("Accept", this.mAccept);
        huc.setRequestProperty(Head_AcceptLanguage, this.mAcceptLanguage);
        if (this.mLen >= 0) {
            huc.setRequestProperty("Content-length", String.valueOf(this.mLen));
        }
        for (int i = 0; i < this.Heads.size(); i++) {
            huc.setRequestProperty(HeadKey(i), HeadValue(i));
        }
    }

    private FileOutputStream getOutStreamByPath(String sFile) throws FileNotFoundException {
        FileOutputStream fw = null;
        if (sFile.length() > 0) {
            File f = new File(sFile);
            if (f.exists()) {
                f.delete();
                f = new File(sFile);
            }
            fw = new FileOutputStream(f);
        }
        return fw;
    }

    public static Vector<String> regex(String msg, String regex) {
        Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher m = p.matcher(msg);
        Vector<String> vs = new Vector<String>();
        while (m.find()) {
            vs.add(m.group());
        }
        return vs;
    }

    public String readline(InputStream in) throws IOException {
        byte[] bts = new byte[1024];
        byte[] b = new byte[1];
        int i = 0;
        String s = null;
        while (in.read(b) > 0) {
            if (s == null) {
                s = "";
            }
            if (b[0] == 13) {
                continue;
            }
            if (b[0] == 10) {
                break;
            }
            bts[i++] = b[0];
            if (i >= bts.length) {
                s += new String(bts);
                i = 0;
            }
        }
        if (i > 0) {
            if (s == null) {
                s = new String(bts, 0, i);
            } else {
                s = s + new String(bts, 0, i);
            }
        }
        return s;
    }

    private HttpResponse getHttpResponse(HttpURLConnection huc, OutputStream os) throws IOException {
        HttpResponse hr = new HttpResponse();
        huc.connect();
        huc.setReadTimeout(connTimeout);//建立连接后的读取超时设置
        huc.setConnectTimeout(connTimeout);
        hr.setCode(huc.getResponseCode());
        hr.setMsg(huc.getResponseMessage());
        int Count = huc.getHeaderFields().size();
        for (int i = 0; i < Count; i++) {
            String key = huc.getHeaderFieldKey(i);
            String value = huc.getHeaderField(i);
            if (key == null) {
                continue;
            }
            if (key.length() == 0) {
                continue;
            }
            hr.addHead(key, value);
        }
        boolean HasEncode = false;
        String ct = hr.HeadValue("Content-Type");
        if (ct != null) {
            String[] cts = ct.split("=");
            if (cts.length > 1) {
                this.setEncode(cts[1]);
                HasEncode = true;
            }
        }
        InputStream in = null;
        try {
            in = huc.getInputStream();
        } catch (Exception e) {
            in = huc.getErrorStream();
        }
        if (os != null) {// 当下载成文件时,不做任何处理
            byte[] bt = new byte[1024];
            int c = in.read(bt);
            while (c > 0) {
                os.write(bt, 0, c);
                c = in.read(bt);
            }
            in.close();
            os.close();
            hr.setBody("");
            return hr;
        }
        HasEncode = this.httpEncode.length() > 0;
        StringBuffer sbBody = new StringBuffer();
        if (!HasEncode) {
            String line = readline(in);
            while (line != null) {
                sbBody.append(line);
                sbBody.append("\r\n");
                line = line.toLowerCase();
                if (line.matches(".*[;\\s]charset=[^'\"\\s]+['\"\\s/>].*")) {
                    line = HttpClient.regex(line, "charset=[^'\"\\s]+(?=['\"\\s/>])").get(0);
                    line = line.split("=")[1].trim();
                    this.setEncode(line);
                    break;
                }
                if (line.matches(".*\\sencoding=['\"]{0,1}[^'\"\\s]+['\"\\s/>].*")) {
                    line = HttpClient.regex(line, "encoding=['\"]{0,1}[^'\"\\s]+(?=['\"\\s/>])")
                            .get(0);
                    line = line.split("=")[1].trim().replaceAll("['\"]", "");
                    this.setEncode(line);
                    break;
                }
                line = readline(in);
            }
        }
        BufferedReader br = null;
        if (this.httpEncode.length() == 0) {
            br = new BufferedReader(new InputStreamReader(in));
        } else {
            br = new BufferedReader(new InputStreamReader(in, this.httpEncode));
        }
        String line = br.readLine();
        while (line != null) {
            sbBody.append(line);
            sbBody.append("\r\n");
            line = br.readLine();
        }
        hr.setBody(sbBody.toString());
        try {
            br.close();
        } catch (Exception ex) {}
        return hr;
    }

    public HttpResponse GET(String sUrl, int sec, int times) throws IOException {
        for (; times > 0; times--) {
            try {
                return this.GET(sUrl, "");
            } catch (Exception e) {
                try {
                    Thread.sleep(sec * 1000);
                } catch (Exception ex) {}
            }
        }
        return this.GET(sUrl, "");
    }

    public HttpResponse GET(String sUrl) throws IOException {
        return this.GET(sUrl, "");
    }

    public HttpResponse GET(String sUrl, String sFile) throws IOException {
        return this.GET(sUrl, this.getOutStreamByPath(sFile));
    }

    public HttpResponse GET(String sUrl, OutputStream os) throws IOException {
        URL url = new URL(sUrl);
        HttpURLConnection huc;
        if (this.proxy == null) {
            huc = (HttpURLConnection) url.openConnection();
        } else {
            huc = (HttpURLConnection) url.openConnection(proxy);
        }
        huc.setDoOutput(false);
        this.setHeads(huc);
        huc.setRequestMethod("GET");
        HttpResponse hr = this.getHttpResponse(huc, os);
        try {
            huc.disconnect();
        } catch (Exception ex) {}
        return hr;
    }

    public HttpResponse Post(String sUrl, String sData, int sec, int times) throws IOException {
        for (; times > 0; times--) {
            try {
                return this.Post(sUrl, sData, "");
            } catch (Exception e) {
                try {
                    Thread.sleep(sec * 1000);
                } catch (Exception ex) {}
            }
        }
        return Post(sUrl, sData, "");
    }

    public HttpResponse Post(String sUrl, String sData) throws IOException {
        return this.Post(sUrl, sData, "");
    }

    public HttpResponse Post(String sUrl, String sData, String saveFile) throws IOException {
        return this.Post(sUrl, sData, this.getOutStreamByPath(saveFile));
    }

    public HttpResponse Post(String sUrl, String sData, OutputStream os) throws IOException {
        URL url = new URL(sUrl);
        HttpURLConnection huc;
        if (this.proxy == null) {
            huc = (HttpURLConnection) url.openConnection();
        } else {
            huc = (HttpURLConnection) url.openConnection(proxy);
        }
        huc.setRequestMethod("POST");
        huc.setDoOutput(true);
        this.setHeads(huc);
        OutputStreamWriter ow;
        ow = new OutputStreamWriter(huc.getOutputStream(), httpEncode);
        ow.write(sData);
        ow.flush();
        ow.close();
        HttpResponse hr = this.getHttpResponse(huc, os);
        try {
            huc.disconnect();
        } catch (Exception ex) {}
        return hr;
    }

    public class HttpResponse {

        private int mCode;

        private String mMsg, mBody, cookie;

        private Vector<String> mHeads = new Vector<String>();

        private Properties cookies = new Properties();

        public Properties getCookies() {
            return cookies;
        }

        public String getCookie() {
            return cookie;
        }

        public String getJSESSIONID() {
            return cookies.getProperty("JSESSIONID");
        }

        protected void addHead(String key, String value) {
            if (key == null) {
                key = "";
            }
            if (value == null) {
                value = "";
            }
            this.mHeads.add(key + ":" + value);
            if (key.toLowerCase().matches("set-cookie")) {
                cookie = value;
                String[] cks = value.split(";");
                for (String ck : cks) {
                    String[] kv = ck.trim().split("=");
                    if (kv.length < 2) {
                        continue;
                    }
                    for (int j = 2; j < kv.length; j++) {
                        kv[1] = String.format("%s=%s", kv[1], kv[j]);
                    }
                    cookies.put(kv[0], kv[1]);
                }
            }
        }

        protected void setCode(int Code) {
            this.mCode = Code;
        }

        public int getCode() {
            return this.mCode;
        }

        protected void setMsg(String Msg) {
            this.mMsg = Msg;
        }

        protected void setBody(String Body) {
            this.mBody = Body;
        }

        public int ResponseCode() {
            return mCode;
        }

        public String ResponseMsg() {
            return mMsg;
        }

        public String Body() {
            return mBody;
        }

        public int HeadSize() {
            return mHeads.size();
        }

        public String HeadKey(int index) {
            if (index < 0) {
                return "";
            }
            if (index >= mHeads.size()) {
                return "";
            }
            return mHeads.get(index).split(":")[0];
        }

        public String HeadValue(int index) {
            if (index < 0) {
                return "";
            }
            if (index >= mHeads.size()) {
                return "";
            }
            String[] ts = mHeads.get(index).split(":");
            String t = ts[1];
            for (int i = 2; i < ts.length; i++) {
                t = String.format("%s:%s", t, ts[i]);
            }
            return t;
        }

        public String HeadValue(String key) {
            key = key.toLowerCase();
            for (int i = 0; i < this.HeadSize(); i++) {
                if (this.HeadKey(i).toLowerCase().matches(key)) {
                    return this.HeadValue(i);
                }
            }
            return "";
        }

        public String HeadToString() {
            return HeadToString(":", "\r\n");
        }

        public String HeadToString(String part1, String part2) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < this.HeadSize(); i++) {
                sb.append(this.HeadKey(i));
                sb.append(part1);
                sb.append(this.HeadValue(i));
                sb.append(part2);
            }
            return sb.toString();
        }
    }
}
