package cn.enaium.foxbase.command;

public interface Command {

	boolean run(String[] args);

	String[] usage();

}
