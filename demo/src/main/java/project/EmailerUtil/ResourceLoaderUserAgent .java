package project.EmailerUtil;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextUserAgent;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;

// @EnableJpaRepositories
// @Configuration
// class ResourceLoaderUserAgent extends ITextUserAgent {

//     public ResourceLoaderUserAgent(ITextOutputDevice outputDevice) {
//         super(outputDevice);
//     }

//     protected InputStream resolveAndOpenStream(String uri) {

//         InputStream is = super.resolveAndOpenStream(uri);
//         String fileName = "";
//         try {
//             String[] split = uri.split("/");
//             fileName = split[split.length - 1];
//         } catch (Exception e) {
//             return null;
//         }

//         if (is == null) {
//             // Resource is on the classpath
//             try {
//                 is = ResourceLoaderUserAgent.class.getResourceAsStream("/" + fileName);
//             } catch (Exception e) {
//             }



//         }
//         return is;
//     }
// }
