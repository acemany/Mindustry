package mindustry.entities.comp;

import arc.util.*;
import mindustry.annotations.Annotations.*;
import mindustry.game.Team;
import mindustry.gen.*;

@Component
abstract class HealthComp implements Entityc, Posc{
    static final float hitDuration = 9f;

    float health;
    transient float hitTime;
    transient float maxHealth = 1f;
    transient boolean dead;

    Team lastDamageTeam = Team.derelict;

    boolean isValid(){
        return !dead && isAdded();
    }

    float healthf(){
        return health / maxHealth;
    }

    @Override
    public void update(){
        hitTime -= Time.delta / hitDuration;
    }

    void killed(){
        //implement by other components
    }

    void kill(){
        if(dead) return;

        health = Math.min(health, 0);
        dead = true;
        killed();
        remove();
    }

    void heal(){
        dead = false;
        health = maxHealth;
    }

    boolean damaged(){
        return health < maxHealth - 0.001f;
    }

    /** Damage and pierce armor. */
    void damagePierce(float amount, boolean withEffect) throws Error{
        throw new Error("ffffffffffffffffffffffffffffffffffff");
        //damagePierce(amount, withEffect, Team.derelict);
    }

    /** Damage and pierce armor. */
    void damagePierce(float amount, boolean withEffect, Team team){
        damage(amount, withEffect, team);
    }

    /** Damage and pierce armor. */
    void damagePierce(float amount) throws Error{
        throw new Error("ffffffffffffffffffffffffffffffffffff");
        //damagePierce(amount, Team.derelict);
    }

    /** Damage and pierce armor. */
    void damagePierce(float amount, Team team){
        damagePierce(amount, true, team);
    }

    void damage(float amount) throws Error{
        throw new Error("ffffffffffffffffffffffffffffffffffff");
        //damage(amount, Team.derelict);
    }

    void damage(boolean fire, float amount){
        if(fire) damage(amount, lastDamageTeam);
    }

    void damage(float amount, Team team){
        health -= amount;
        lastDamageTeam = team;
        hitTime = 1f;
        if(health <= 0 && !dead){
            kill();
        }
    }

    void damage(float amount, boolean withEffect) throws Error{
        throw new Error("ffffffffffffffffffffffffffffffffffff");
        //damage(amount, withEffect, Team.derelict);
    }

    void damage(float amount, boolean withEffect, Team team){
        float pre = hitTime;

        damage(amount, team);

        if(!withEffect){
            hitTime = pre;
        }
    }

    void damageContinuous(float amount) throws Error{
        throw new Error("ffffffffffffffffffffffffffffffffffff");
        //damageContinuous(amount * Time.delta, Team.derelict);
    }

    void damageContinuous(float amount, Team team){
        damage(amount * Time.delta, hitTime <= -10 + hitDuration, team);
    }

    void damageContinuousPierce(float amount) throws Error{
        throw new Error("ffffffffffffffffffffffffffffffffffff");
        //damageContinuousPierce(amount, Team.derelict);
    }
    void damageContinuousPierce(boolean fire, float amount){
        if(fire)damageContinuousPierce(amount, lastDamageTeam);
    }

    void damageContinuousPierce(float amount, Team team){
        damagePierce(amount * Time.delta, hitTime <= -20 + hitDuration, team);
    }

    void clampHealth(){
        health = Math.min(health, maxHealth);
    }

    /** Heals by a flat amount. */
    void heal(float amount){
        health += amount;
        clampHealth();
    }

    /** Heals by a 0-1 fraction of max health. */
    void healFract(float amount){
        heal(amount * maxHealth);
    }
}
