package tomcat_implements;

import servlet_standard.HttpServletRequest;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Scanner;

public class HttpServletRequestBuilder {
    public static HttpServletRequest build(InputStream inputStream) throws UnsupportedEncodingException {
        // 按照 HTTP 协议请求的格式，从 inputStream 中读取内容，填充到 HttpServletRequestImpl 对象中
        HttpServletRequestImpl req = new HttpServletRequestImpl();

        // 为了方便按行读取，使用 Scanner 进行读取
        // 默认字符集实际上不是 utf-8
        Scanner s = new Scanner(inputStream, "utf-8");

        // 1. 读取请求行
        readAndParseRequestLine(s, req);
        // 2. 读取请求头
        readAndParseRequestHeaders(s, req);
        // 3. 读取请求体【暂时不处理】

        return req;
    }

    private static void readAndParseRequestHeaders(Scanner s, HttpServletRequestImpl req) {
        while (true) {
            String line = s.nextLine();
            if (line.length() == 0) {
                break;
            }

            String[] split = line.split(":");
            String name = split[0].trim();
            String value = split[1].trim();

            req.setHeader(name, value);
        }
    }

    private static void readAndParseRequestLine(Scanner s, HttpServletRequestImpl req) throws UnsupportedEncodingException {
        // 方法 URL 协议版本
        String line = s.nextLine();
        String[] split = line.split(" ");
        req.setMethod(split[0].toUpperCase());

        // URL 中可能包含 query string 部分
        // /hello?target=peixinchen
        parseUrl(split[1], req);

        req.setProtocol(split[2]);
    }

    private static void parseUrl(String url, HttpServletRequestImpl req) throws UnsupportedEncodingException {
        // /hello?target=peixinchen&age=34
        String[] split = url.split("\\?");
        if (split.length == 1) {
            req.setRequestURI(url);
            return;
        }

        req.setRequestURI(split[0]);
        String[] kvs = split[1].split("&");
        for (String kv : kvs) {
            String[] kvGroup = kv.split("=");
            String name = URLDecoder.decode(kvGroup[0], "utf-8");
            String value = URLDecoder.decode(kvGroup[1], "utf-8");

            req.setParameter(name, value);
        }
    }
}
