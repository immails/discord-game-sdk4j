package de.jcm.discordgamesdk.activity;

public class Activity implements AutoCloseable
{
	private long pointer;

	private ActivityTimestamps timestamps;
	private ActivityAssets assets;
	private ActivityParty party;
	private ActivitySecrets secrets;

	public Activity()
	{
		this.pointer = allocate();

		this.timestamps = new ActivityTimestamps(getTimestamps(pointer));
		this.assets = new ActivityAssets(getAssets(pointer));
		this.party = new ActivityParty(getParty(pointer));
		this.secrets = new ActivitySecrets(getSecrets(pointer));
	}

	public void setState(String state)
	{
		if(state.getBytes().length >= 128)
			throw new IllegalArgumentException("max length is 127");
		setState(pointer, state);
	}

	public String getState()
	{
		return getState(pointer);
	}

	public void setDetails(String details)
	{
		if(details.getBytes().length >= 128)
			throw new IllegalArgumentException("max length is 127");
		setDetails(pointer, details);
	}
	public String getDetails()
	{
		return getDetails(pointer);
	}

	public void setType(ActivityType type)
	{
		setType(pointer, type.ordinal());
	}
	public ActivityType getType()
	{
		return ActivityType.values()[getType(pointer)];
	}

	public ActivityTimestamps timestamps()
	{
		return timestamps;
	}
	public ActivityAssets assets()
	{
		return assets;
	}
	public ActivityParty party()
	{
		return party;
	}
	public ActivitySecrets secrets()
	{
		return secrets;
	}

	private native long allocate();
	private native void free(long pointer);

	private native void setState(long pointer, String state);
	private native String getState(long pointer);

	private native void setDetails(long pointer, String details);
	private native String getDetails(long pointer);

	private native void setType(long pointer, int type);
	private native int getType(long pointer);

	private native long getTimestamps(long pointer);
	private native long getAssets(long pointer);
	private native long getParty(long pointer);
	private native long getSecrets(long pointer);

	@Override
	public void close()
	{
		free(pointer);
	}

	public long getPointer()
	{
		return pointer;
	}
}
