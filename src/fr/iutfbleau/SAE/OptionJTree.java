package fr.iutfbleau.SAE;
public class OptionJTree {
    private int idMenu;
    private String nomMenu;
    private boolean isClicked;
    
    public OptionJTree (int idMenu, String nomMenu, boolean isClicked){
	this.idMenu = idMenu;
	this.nomMenu = nomMenu;
	this.isClicked = isClicked;
    }

    public int getId(){
	return this.idMenu;
    }

    public boolean isClicked(){
	return this.isClicked;
    }

    public void setIsClickedTrue(){
	this.isClicked=true;
    }

    @Override
    public String toString(){
	return this.nomMenu;
    }
}
	
