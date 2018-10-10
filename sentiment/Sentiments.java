package sentiment;

public class Sentiments {
	  private String polarity;
	  private String text;
	  private Double polarityConfidence;
	 
	  public String getPolarity() {
	    return polarity;
	  }
	  public void setPolarity(String polarity) {
	    this.polarity = polarity;
	  }
	  public String getText() {
	    return text;
	  }
	  public void setText(String text) {
	    this.text = text;
	  }
	  public Double getPolarityConfidence() {
	    return polarityConfidence;
	  }
	  public void setPolarityConfidence(Double confidence) {
	    this.polarityConfidence = confidence;
	  }
	}
