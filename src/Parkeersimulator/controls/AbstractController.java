package Parkeersimulator.controls;
import Parkeersimulator.Models.*;

public abstract class AbstractController {
	protected Simulator simulator;
	
public AbstractController(Simulator sim) {
	this.simulator = sim;
 	}
}
