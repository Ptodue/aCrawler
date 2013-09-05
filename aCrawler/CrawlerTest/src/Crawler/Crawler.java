package Crawler;

abstract class Crawler {
	Intialize init;

	abstract void loading();
	abstract void filter();
	abstract void doParse();
	abstract Queue run();
	

}
