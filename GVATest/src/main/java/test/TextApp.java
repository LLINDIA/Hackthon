package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionScopes;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
//import com.google.cloud.vision.v1.Feature.Type;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.Status;
import com.google.api.services.vision.v1.model.WebDetection;
import com.google.api.services.vision.v1.model.WebEntity;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

public class TextApp {

	  private static final String APPLICATION_NAME = "Google-VisionTextSample/1.0";
	  
	  private static final int MAX_RESULTS = 10;
	  private static final int BATCH_SIZE = 10;
	  
	  private final Vision vision;
	  
	public static void main(String[] args)  throws IOException, GeneralSecurityException {
		TextApp app = new TextApp(TextApp.getVisionService());
		List<Path> files = new ArrayList<Path>();
		// files.add(Paths.get("D:\\Ankit\\Pics\\Tikona_Trek_July2017\\20170701_112342.jpg"));
		files.add(Paths.get("C:\\Users\\AnkitA\\Desktop\\bajaj-px-80-f-gx-1-mixer-grinder-original-imafyagzggz7espm.jpeg"));
		// files.add(Paths.get("D:\\Hackathon\\images\\PRD29604_19765_B.jpg"));
		List<ImageText> output = app.detectText(files);
		
		List<WebEntity> output1 = app.detectWeb(files);
		
		for(ImageText imgTxt : output) {
			System.out.println();
			System.out.println(imgTxt.path().toString());
			
			for(EntityAnnotation entAnt:imgTxt.textAnnotations()) {
				System.out.println(entAnt.getDescription());
			}
			
		}
		
	}
	
	  public TextApp(Vision vision) {
		    this.vision = vision;
		  }
	  /**
	   * Connects to the Vision API using Application Default Credentials.
	   */
	  public static Vision getVisionService() throws IOException, GeneralSecurityException {
	    GoogleCredential credential =
	        GoogleCredential.getApplicationDefault().createScoped(VisionScopes.all());
	    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
	    return new Vision.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, credential)
	            .setApplicationName(APPLICATION_NAME)
	            .build();
	  }
	  
	  /**
	   * Gets up to {@code maxResults} text annotations for images stored at {@code paths}.
	   */
	  public ImmutableList<ImageText> detectText(List<Path> paths) {
	    ImmutableList.Builder<AnnotateImageRequest> requests = ImmutableList.builder();
	    try {
	      for (Path path : paths) {
	        byte[] data;
	        data = Files.readAllBytes(path);
	        requests.add(
	            new AnnotateImageRequest()
	                .setImage(new Image().encodeContent(data))
	                .setFeatures(ImmutableList.of(
	                    new Feature()
	                        .setType("LABEL_DETECTION")
	                        .setMaxResults(MAX_RESULTS))));
	      }
	      
	      

	      Vision.Images.Annotate annotate =
	          vision.images()
	              .annotate(new BatchAnnotateImagesRequest().setRequests(requests.build()));
	      // Due to a bug: requests to Vision API containing large images fail when GZipped.
	      annotate.setDisableGZipContent(true);
	      BatchAnnotateImagesResponse batchResponse = annotate.execute();
	      assert batchResponse.getResponses().size() == paths.size();

	      ImmutableList.Builder<ImageText> output = ImmutableList.builder();
	      for (int i = 0; i < paths.size(); i++) {
	        Path path = paths.get(i);
	        AnnotateImageResponse response = batchResponse.getResponses().get(i);
	        output.add(
	            ImageText.builder()
	                .path(path)
	                .textAnnotations(
	                    MoreObjects.firstNonNull(
	                        response.getLabelAnnotations(),
	                        ImmutableList.<EntityAnnotation>of()))
	                .error(response.getError())
	                .build());
	      }
	      return output.build();
	    } catch (IOException ex) {
	      // Got an exception, which means the whole batch had an error.
	      ImmutableList.Builder<ImageText> output = ImmutableList.builder();
	      for (Path path : paths) {
	        output.add(
	            ImageText.builder()
	                .path(path)
	                .textAnnotations(ImmutableList.<EntityAnnotation>of())
	                .error(new Status().setMessage(ex.getMessage()))
	                .build());
	      }
	      return output.build();
	    }
	  }	  
	  
	  public ImmutableList<WebEntity> detectWeb(List<Path> paths) {
		    ImmutableList.Builder<AnnotateImageRequest> requests = ImmutableList.builder();
		    try {
		      for (Path path : paths) {
		        byte[] data;
		        data = Files.readAllBytes(path);
		        requests.add(
		            new AnnotateImageRequest()
		                .setImage(new Image().encodeContent(data))
		                .setFeatures(ImmutableList.of(
		                    new Feature()
		                        .setType("WEB_DETECTION")
		                        .setMaxResults(MAX_RESULTS))));
		      }
		      
		      

		      Vision.Images.Annotate annotate =
		          vision.images()
		              .annotate(new BatchAnnotateImagesRequest().setRequests(requests.build()));
		      // Due to a bug: requests to Vision API containing large images fail when GZipped.
		      annotate.setDisableGZipContent(true);
		      BatchAnnotateImagesResponse batchResponse = annotate.execute();
		      assert batchResponse.getResponses().size() == paths.size();
		      
		      ImmutableList.Builder<WebEntity> output = ImmutableList.builder();
		      for (int i = 0; i < paths.size(); i++) {
		        Path path = paths.get(i);
		        AnnotateImageResponse response = batchResponse.getResponses().get(i);
		        WebDetection webDetection = response.getWebDetection();
		        System.out.println("Entity:Id:Score");
		        System.out.println("===============");
		        for (WebEntity entity : webDetection.getWebEntities()) {
		          System.out.println(entity.getDescription() + " : " + entity.getEntityId() + " : "
		              + entity.getScore());
		          output.add(entity);
		        }
		      }

		      return output.build();
		    } catch (IOException ex) {
		      // Got an exception, which means the whole batch had an error.
			  ImmutableList.Builder<WebEntity> output = ImmutableList.builder();
		      for (Path path : paths) {
		        output.add(
		            new WebEntity());
		      }
		      return output.build();
		    }
		  }
}
