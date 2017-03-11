package com.oxchains.handler;

import com.google.common.base.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.AbstractResponseHandler;

import java.io.IOException;
import java.io.InputStream;

/**
 * FluentResponseHandler
 *
 * @author liuruichao
 * Created on 2016/12/29 18:13
 */
public class FluentResponseHandler extends AbstractResponseHandler<String> {

    @Override
    public String handleResponse(HttpResponse response) throws IOException {
        //final StatusLine statusLine = response.getStatusLine();
        final HttpEntity entity = response.getEntity();
        /*if (statusLine.getStatusCode() >= 300) {
            EntityUtils.consume(entity);
            throw new HttpResponseException(statusLine.getStatusCode(),
                    statusLine.getReasonPhrase());
        }*/
        return entity == null ? null : handleEntity(entity);
    }

    @Override
    public String handleEntity(HttpEntity entity) throws IOException {
        String result = "";
        InputStream in = null;
        try {
            in = entity.getContent();
            result = IOUtils.toString(in, Charsets.UTF_8);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return result;
    }
}
