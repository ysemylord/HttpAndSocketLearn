package xyb.personal.httpdownload;

public interface DownloadEvent
{
	void percent(long n);
	void state(String s);
	void viewHttpHeaders(String s);
}
