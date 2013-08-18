package game.objects.dep;

public class SpellEffect {
    private int effectId;
    private int min, max;
    private int target;
    private int duration;
    
    public SpellEffect(int id, int duration, String jet, int ET){
        effectId = id;
        this.duration = duration;
        
        min = Integer.parseInt(jet.split("d")[0]);
        max = Integer.parseInt(jet.split("d")[1].split("\\+")[0]);
        int fix = Integer.parseInt(jet.split("d")[1].split("\\+")[1]);
        
        min += fix;
        max += fix;
        
        target = ET;
    }
}
