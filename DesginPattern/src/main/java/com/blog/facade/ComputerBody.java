package com.blog.facade;

public class ComputerBody {
    private final CPU cpu;
    private final Memory memory;
    private final HardDisk hardDisk;

    public ComputerBody() {
        this.cpu = new CPU();
        this.memory = new Memory();
        this.hardDisk = new HardDisk();
    }

    public void startButton() {
        cpu.init();
        memory.checkMemory();
        hardDisk.BootHardDrive();
        System.out.println("Booting started...");
    }
}
