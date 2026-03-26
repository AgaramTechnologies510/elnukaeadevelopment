package com.agaram.eln.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

//import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Converter extends AbstractHttpMessageConverter<Object> {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	@SuppressWarnings("deprecation")
	public Converter() {
		super(MediaType.APPLICATION_JSON_UTF8, new MediaType("application", "*+json", DEFAULT_CHARSET));
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		String contenttype = inputMessage.getHeaders().get("Content-Type").get(0);
		String encoding = inputMessage.getHeaders().get("accept-encoding") != null
				? inputMessage.getHeaders().get("accept-encoding").get(0)
				: inputMessage.getHeaders().get("accept") != null ?
						inputMessage.getHeaders().get("accept").get(0):"";

		String contendencoding = "";

		if (inputMessage.getHeaders().containsKey("content-encoding")) {
			contendencoding = inputMessage.getHeaders().get("content-encoding").get(0);
			System.out.println("content-encoding header : " + contendencoding);
		} else {
			contendencoding = "normal";
		}

		String decryptionkey = "1234567812345678";

		System.out.println("contant type :" + contenttype);
		System.out.println("encoding :" + encoding);
		System.out.println("contendencoding :" + contendencoding);
		if (contenttype.equalsIgnoreCase("application/json;charset=UTF-8")
				&& encoding.contains("gzip")
//				&& (encoding.equalsIgnoreCase("gzip, deflate") || encoding.equalsIgnoreCase("gzip, deflate, br")|| encoding.equalsIgnoreCase("gzip, deflate, br, zstd"))
				&& contendencoding.equalsIgnoreCase("gzip")
				) {
			System.out.println("come decryption");
			return mapper.readValue(decrypt(inputMessage.getBody(), decryptionkey), clazz);
		} else {
			System.out.println("normal");
			return mapper.readValue(inputMessage.getBody(), clazz);
		}

	}

	private byte[] compress(byte[] data) throws IOException {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		try (GZIPOutputStream gzipStream = new GZIPOutputStream(byteStream)) {
			gzipStream.write(data);
		}
		return byteStream.toByteArray();
	}

	@Override
	protected void writeInternal(Object o, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		jakarta.servlet.http.HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String requestUrl = request.getRequestURI();
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------");
		System.out.println("ZipData" + requestUrl);
		
		if (requestUrl.contains("/ELNPOSTGRE-0.0.1-SNAPSHOT")) {
		    requestUrl = requestUrl.substring("/ELNPOSTGRE-0.0.1-SNAPSHOT".length());
		} else if (requestUrl.contains("/ELN-0.0.1-SNAPSHOT")) {
		    requestUrl = requestUrl.substring("/ELN-0.0.1-SNAPSHOT".length());
		} else if (requestUrl.contains("/ELNCLOUDPOST-0.0.1-SNAPSHOT")) {
		    requestUrl = requestUrl.substring("/ELNCLOUDPOST-0.0.1-SNAPSHOT".length());
		} 

		List<String> uncompressedUrls = Arrays.asList("/protocol/uploadprotocolsfile",
				"/protocol/Uploadprotocolimage", "/protocol/removeprotocolimage", "/protocol/removeprotocolimagesql", 
				"/protocol/loadprotocolfiles", "/protocol/uploadvideo", "/protocol/uploadvideosql", 
				"/protocol/removeprotocolvideo", "/protocol/removeprotocolvideossql", "/protocol/uploadprotocolsorderfile",
				"/protocol/uploadprotocolsorderfilesql", "/protocol/Uploadprotocolorderimage",
				"/protocol/Uploadprotocolorderimagesql", "/protocol/removeprotocoorderlimage",
				"/protocol/removeprotocoorderlimagesql", "/protocol/loadprotocolorderfiles",
				"/protocol/uploadprotocolordervideo", "/protocol/downloadprotocolordervideosql",
				"/protocol/removeprotocolordervideo", "/protocol/removeprotocolordervideossql",
				"/protocol/downloadprotocolimage", "/Lims/getSheetsFromELN", "/Lims/downloadSheetFromELN",
				"/Lims/downloadResultFromELN", "/Lims/updateSheetsParameterForELN", "/Lims/getAttachmentsForLIMS",
				"/Lims/getOrdersFromELN", "/Lims/getOrderTagFromELN", "/Lims/getSiteFromELN", "/Lims/getUsersFromELN",
				"/Instrument/uploadsheetimagesSql", "/Instrument/uploadsheetimages", "/Instrument/downloadsheetimages",
				"/Instrument/downloadsheetimagessql", "/Restcall/InsertElnOrders", "/Restcall/InsertELNordersFromLims",
                "/evaluateParser",
        		"/findByStatus",
				"/getMethod", "/getParserData", "/getMethodDelimiter", "/getParserMethod", "/getDelimiters",
				"/getSubParserMethod", "/getParserFieldTechniqueListByMethodKey", "/getmethodversion",
//				"/protocol/Uploadprotocolimagesql",
//				"/protocol/uploadprotocolsfilesql",
        		"/MethodExportController/exportMethods",
        		"/MethodImportController/importMethods",
        		"/downloadFile/{fileName}",
				// "/getFileData",
				"/Login/LoadSitewithoutgzip", "/Login/Logintenat/","/Login/importchemdata","/Login/CheckUserPassword",
				    "/DashBoardDemo/Getdashboardordercount",
	                "/DashBoardDemo/Getdashboardorders","/documenteditor/api/wordeditor/Import","/smartdevice/Getdata", "/documentviewer/api/wordeditor/Import"
	                ,"/documenteditor/Import","/documenteditor/api/wordeditor/RestrictEditing","/Instrument/uploadfilessheetfolder"
	                ,"/Instrument/uploadfilesprotocolfolder","/Barcode/Getbarcodefileoncode","/Barcode/GetbarcodefilecodeonOrderscreen"
	                ,"/Barcode/handleBarcode","/iotconnect/getInstcategory","/iotconnect/getInstruments","/protocol/GetAllprotocols",
	                "/materialinventory/getMaterialAllInventory","/Instrument/GetSheettagdataonOrdercode","/Instrument/GetSheettagdataonSequenceid",
	                "/protocol/downloadprotocolfile/{fileid}/{tenant}/{filename}/{extension}/{ontabkey}",
                    "/protocol/downloadprotocolfilewithstepname/{tenantid}/{screen}/{filetype}/{protocolname}/{sectionname}/{stepname}/{editorname}/{filename}/{extension}",
                    "/protocol/downloadprotocolfilewithoutstepname/{tenantid}/{screen}/{filetype}/{protocolname}/{filename}/{extension}","/protocol/getSingleProtocolcontent"
                    ,"/protocol/downloadreadablefiles","/protocol/downloadprotocolfilesql/{fileid}/{filename}/{extension}/{ontabkey}",
                    "/Instrument/downloadsheetfileforfolder/{multitenant}/{tenant}/{fileid}","/Login/chat","/Login/chatcontent","/Instrument/downloadsheetimagesforbase64/{fileid}/{tenant}/{filename}/{extension}"
		);
		
		Integer secondindex = StringUtils.ordinalIndexOf(requestUrl, "/", 3);
		String suburl = "";
		if(secondindex>-1)
		{
			suburl = requestUrl.substring(0, secondindex);
		}
		byte[] uncompressedBytes = mapper.writeValueAsBytes(o);
		byte[] compressedBytes;

		if (uncompressedUrls.contains(requestUrl)||uncompressedUrls.contains(suburl)) {
		    // Don't compress the response
		    compressedBytes = uncompressedBytes;
		} else {
		    // Compress the response
		    compressedBytes = compress(uncompressedBytes);
		}
		outputMessage.getBody().write(compressedBytes);
	}

	private InputStream decrypt(InputStream inputStream, String key) {
		InputStream in;
		byte[] bytes = null;
		try {
			in = new GZIPInputStream(inputStream);
			bytes = IOUtils.toByteArray(in);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return new ByteArrayInputStream(bytes);
	}

}
