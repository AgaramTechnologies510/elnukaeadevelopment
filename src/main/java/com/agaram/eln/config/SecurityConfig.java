package com.agaram.eln.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
//import antlr.collections.List;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	@Lazy
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	private CorsConfigurationSource corsConfigurationSource;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
				.allowedHeaders("*");
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@SuppressWarnings("removal")
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
				.authorizeHttpRequests(auth -> {
					try {
						auth
//			    .requestMatchers("/**").permitAll();
								.requestMatchers("/authenticate", "/multitenant/otpvarification",
										"/multitenant/afterotpverified", "/registerMail", "/multitenant/sendotp",
										"/Login/LoadSite", "/Login/LoadDomain", "/Login/azureusertokengenrate",
										"/Login/CheckUserAndPassword", "/Login/updateActiveUserTime",
										"/Login/createuserforazure", "/Login/UpdatePassword", "/Login/Validateuser",
										"/Login/LinkLogin", "/User/InsertUpdateUserGroup", "/User/InsertUpdateUser",
										"/User/getActiveUserCount", "/User/GetUsers", "/Login/CheckUserPassword",
										"/AuditTrail/AuditConfigurationrecord", "/User/GetPasswordPolicySitewise",
										"/User/Createcentraliseduser", "/User/Getallcentraliseduser",
										"/User/Getcentraliseduserbyid", "/Instrument/GetInstrumentParameters",
										"/Instrument/Insertshareorder", "/Instrument/GetsharedtomeorderStatus",
										"/Instrument/GetResultsharedfileverContent", "/Instrument/SaveSharedResultfile",
										"/Instrument/SharedCloudUploadattachments",
										"/Instrument/SharedUploadattachments",
										"/Instrument/SharedClouddeleteattachments",
										"/Instrument/shareddeleteattachments",
										"/Instrument/SharedClouddownloadattachments",
										"/Instrument/Shareddownloadattachments", "/Instrument/Sharedattachment",
										"/Instrument/Getorderforlink", "/Instrument/getmetatag",
										"/Instrument/downloadNonCloud/{param}/{tenant}/{fileid}",
										"/Instrument/downloadparserNonCloud/{param}/{tenant}/{fileid}",
										"/Instrument/download/{param}/{fileid}",
										"/Instrument/downloadparser/{param}/{fileid}",
										"/Instrument/Sharedcloudattachment", "/multitenant/Registertenant",
										"/multitenant/Registertenantid", "/multitenant/Registercustomersubscription",
										"/multitenant/Validatetenant", "/multitenant/checktenantid",
										"/multitenant/tenantlogin", "/multitenant/Getalltenant",
										"/multitenant/Gettenantonid", "/multitenant/Updatetenant",
										"/multitenant/Initiatetenant", "/multitenant/Updaprofiletetenant",
										"/multitenant/checkusermail", "/multitenant/tenantcontactno",
										"/multitenant/Completeregistration", "/multitenant/updatetenantadminpassword",
										"/reports/cloudsaveDocxsReport", "/reports/getSheetLSfileWithFileCode",
										"/reports/getLSdocreportsLst", "/reports/Getorderbytype",
										"/reports/getFilecontentforSheet", "/reports/Getorderbytype", "/",
										"/User/profile/*", "/User/Cloudprofile/*", "/Login/Logout",
										"/Instrument/Unshareorderto", "/User/GetUserslocal",
										"/multitenant/ValidatetenantByID", "/multitenant/ValidatetenantByName",
										"/User/getUserOnCode", "/helpdocument/gethelpnodes",
										"/helpdocument/getdocumentonid", "/helpdocument/getnodeonpage",
										"/helpdocument/getdocumentcontent", "/helpdocument/helpdownload/{fileid}",
										"/User/validatemailaddress", "/Login/limsloginusertokengenarate",
										"/multitenant/Registerinvoice", "/Lims/getSheetsFromELN",
										"/Lims/downloadSheetFromELN", "/Restcall/InsertELNordersFromLims",
										"/protocol/downloadprotocolimage/{fileid}/{tenant}/{filename}/{extension}",
										"/protocol/downloadprotocolfile/{fileid}/{tenant}/{filename}/{extension}",
										"/Instrument/downloadsheetimages/{fileid}/{tenant}/{filename}/{extension}",
										"/protocol/downloadprotocolfile/{fileid}/{tenant}/{filename}/{extension}/{ontabkey}",
										"/Instrument/downloadsheetimagestemp/{fileid}/{tenant}/{filename}/{extension}",
										"/Instrument/downloadsheetimagessql/{fileid}/{filename}/{extension}",
										"/Instrument/downloadsheetimagestempsql/{fileid}/{filename}/{extension}",
										"/protocol/downloadprotocolorderimage/{fileid}/{tenant}/{filename}/{extension}",
										"/protocol/downloadprotocolorderfiles/{fileid}/{tenant}/{filename}/{extension}",
										"/protocol/downloadprotocolfilewithstepname/{tenantid}/{screen}/{filetype}/{protocolname}/{sectionname}/{stepname}/{editorname}/{filename}/{extension}",
										"/protocol/downloadprotocolfilewithoutstepname/{tenantid}/{screen}/{filetype}/{protocolname}/{filename}/{extension}",
										"/protocol/downloadprotocolimagesql/{fileid}/{filename}/{extension}",
										"/protocol/downloadprotocolfilesql/{fileid}/{filename}/{extension}/{ontabkey}",
										"/protocol/downloadprotocolorderimagesql/{fileid}/{filename}/{extension}",
										"/protocol/downloadprotocolorderfilesql/{fileid}/{filename}/{extension}",
										"/helpdocument/downloadhelpimage/{fileid}/{tenant}/{filename}/{extension}",
										"/Instrument/Getuserworkflow", "/Instrument/Getuserprojects",
										"/User/GetUserRightsonUser", "/AuditTrail/GetAuditconfigUser",
										"/protocol/downloadprotocolvideo/{fileid}/{tenant}/{filename}/{extension}",
										"/protocol/downloadprotocolvideosql/{fileid}/{filename}/{extension}",
										"/protocol/downloadprotocolordervideo/{fileid}/{tenant}/{filename}/{extension}",
										"/protocol/downloadprotocolordervideosql/{fileid}/{filename}/{extension}",
										"/protocol/downloadprotocolordervideosql/{fileid}/{filename}/{extension}",
										"/protocol/downloadprotocolvideosql/{fileid}/{filename}/{extension}",
										"/Lims/getOrdersFromELN", "/Lims/getOrderTagFromELN", "/Lims/getUsersFromELN",
										"/Lims/getSiteFromELN", "/getMethod", "/multitenant/getPremiumTenant",
										"/transaction/updateMaterialDynamicTable",
										"/transaction/updateMappedTemplateFieldPropsMaterialTable",
										"/multitenant/updateplanrights", "/User/getActiveUserCount",
										"/multitenant/updateplanType", "/Login/getlicense", "/Login/loadmultisite",
										"/Login/LoadSitewithoutgzip", "/Login/Logintenat/{Tenantname}/{Username},",
										"/Login/importchemdata", "/Instrument/downloadsheetimages",
										"/Restcall/getlinkedinuserprofile", "/Freeuserlogin/Createuser",
										"/Freeuserlogin/Loginfreeuser", "/Freeuserlogin/Validateuser",
										"/Freeuserlogin/Setpassword", "/Freeuserlogin/Resetpassword",
										"/Freeuserlogin/Loginfreeuserwithname", "/Login/verifyRecaptcha",
										"/DashBoardDemo/Getdashboardordercount", "/DashBoardDemo/Getdashboardorders",
										"/documenteditor/api/wordeditor/Import", "/smartdevice/Getdata",
										"/documenteditor/Import", "/documentviewer/api/wordeditor/Import",
										"/documenteditor/api/wordeditor/RestrictEditing",
										"/documenteditor/api/wordeditor/SystemClipboard", "/Freeuserlogin/GetStartSkip",
										"/iotconnect/getInstcategory", "/iotconnect/getInstruments",
										"/iotconnect/getRCPortdata", "/Instrument/uploadfilesprotocolfolder",
										"/Instrument/uploadfilessheetfolder", "/Freeuserlogin/ValidateEmail",
										"/Freeuserlogin/Sentotpmail", "/iotconnect/getpreferencedata",
										"/Login/LoginonIOT", "/iotconnect/getOrdersBasedOnmethod",
										"/iotconnect/getEquipmenttype", "/iotconnect/getEquipmentcat",
										"/iotconnect/getEquipment", "/iotconnect/getEquipmentmethod",
										"/protocol-moidification-websocket", "/enableprotocolsocket",
										"/notebook/protocolenable", "/protocol/GetAllprotocols",
										"/materialinventory/getMaterialAllInventory", "/Barcode/insertprints",
										"/Barcode/getprinterJobdetails", "/Barcode/insertnotificationonprint",
										"/AuditTrail/beforloginsilentandmanualRecordHandler", "/updatepimagesocket",
										"/Instrument/GetSheettagdataonOrdercode/{batchcode}",
										"/Instrument/GetSheettagdataonSequenceid/{batchcode}",
										"/Login/SpreadConfig","/Instrument/attachment/{fileid}",
										"/Instrument/downloadprotocolfileforfolder/{multitenant}/{tenant}/{fileid}",
										"/protocol/getSingleProtocolcontent",
										"/Login/ssoConfig","DashBoard/Getglobalsearchorders",
										"/documenteditor/api/wordeditor/GetXLSXContent","/documenteditor/api/wordeditor/GetXLSXfile",
						                "/Instrument/downloadsheetfileforfolder/{multitenant}/{tenant}/{fileid}",
						                "/Instrument/downloadsheetimagesforbase64/{fileid}/{tenant}/{filename}/{extension}"
										)
								.permitAll().anyRequest().authenticated().and()
								// make sure we use stateless session; session won't be used to store user's
								// state.
								.exceptionHandling(
										handling -> handling.authenticationEntryPoint(jwtAuthenticationEntryPoint))
								.sessionManagement(management -> management
										.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
					} catch (Exception e) {

						e.printStackTrace();
					}
				});

		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		http.cors(cors -> cors.configurationSource(corsConfigurationSource));
		return http.build();

	}

}
