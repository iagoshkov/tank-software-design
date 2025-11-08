package ru.mipt.bit.platformer.model;

public class HealthSystem {
    private int currentHealth;
    private int maxHealth;
    private boolean showHealthBar = false;
    
    public HealthSystem() {
        this(100); // здоровье по умолчанию
    }
    
    public HealthSystem(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }
    
    public static HealthSystem createRandomHealth() {
        int health = 80 + (int)(Math.random() * 21); // от 80 до 100
        return new HealthSystem(health);
    }
    
    public void takeDamage(int damage) {
        currentHealth = Math.max(0, currentHealth - damage);
    }
    
    public void heal(int amount) {
        currentHealth = Math.min(maxHealth, currentHealth + amount);
    }
    
    public boolean isAlive() {
        return currentHealth > 0;
    }
    
    public int getCurrentHealth() {
        return currentHealth;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public float getHealthPercentage() {
        return (float) currentHealth / maxHealth;
    }
    
    public void setShowHealthBar(boolean show) {
        this.showHealthBar = show;
    }
    
    public boolean shouldShowHealthBar() {
        return showHealthBar;
    }
}