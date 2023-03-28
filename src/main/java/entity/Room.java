package entity;

import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class Room {
	private String description;
	private String type;
	@BsonProperty("bed_options")
	private String bedOptions;
	@BsonProperty("sleep_count")
	private int sleepCount;
	@BsonProperty("smoking_allowed")
	private boolean smokingAllowed;
	private List<String> tags;

	public Room(String description, String type, String bedOptions, int sleepCount, boolean smokingAllowed,
			List<String> tags) {
		super();
		this.description = description;
		this.type = type;
		this.bedOptions = bedOptions;
		this.sleepCount = sleepCount;
		this.smokingAllowed = smokingAllowed;
		this.tags = tags;
	}

	public Room() {
		super();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBedOptions() {
		return bedOptions;
	}

	public void setBedOptions(String bedOptions) {
		this.bedOptions = bedOptions;
	}

	public int getSleepCount() {
		return sleepCount;
	}

	public void setSleepCount(int sleepCount) {
		this.sleepCount = sleepCount;
	}

	public boolean isSmokingAllowed() {
		return smokingAllowed;
	}

	public void setSmokingAllowed(boolean smokingAllowed) {
		this.smokingAllowed = smokingAllowed;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "Room [description=" + description + ", type=" + type + ", bedOptions=" + bedOptions + ", sleepCount="
				+ sleepCount + ", smokingAllowed=" + smokingAllowed + ", tags=" + tags + "]";
	}
}
