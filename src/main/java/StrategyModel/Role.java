package StrategyModel;

import StrategyModel.Interface.IStrategyAttack;
import StrategyModel.Interface.StrategyDefender;
import StrategyModel.Interface.StrategyDisplay;
import StrategyModel.Interface.StrategyRun;

public abstract class Role {
	public String name;
	
	public IStrategyAttack attack;
	
	public StrategyDefender defender;
	
	public StrategyDisplay display;
	
	public StrategyRun run;


	public void setName(String name) {
		this.name = name;
	}


	public Role setAttack(IStrategyAttack attack) {
		this.attack = attack;
		return this;
	}


	public Role setDefender(StrategyDefender defender) {
		this.defender = defender;
		return this;
	}


	public Role setDisplay(StrategyDisplay display) {
		this.display = display;
		return this;
	}


	public Role setRun(StrategyRun run) {
		this.run = run;
		return this;
	}
	
	public void attack(){
		attack.attack();
	}
	public void display(){
		display.display();
	}
	public void defender(){
		defender.defender();
	}
	public void run(){
		run.run();
	}
	
	
}
