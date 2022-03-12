package main.CMDHandler;

import main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Arrays;
import java.util.List;

public class CMDHandler implements TabExecutor {

    private static final String index = Main.index;

    @Override
    public boolean onCommand(@org.jetbrains.annotations.NotNull CommandSender commandSender, @org.jetbrains.annotations.NotNull Command command, @org.jetbrains.annotations.NotNull String s, @org.jetbrains.annotations.NotNull String[] strings) {
        if (commandSender.isOp()) {
            if (s.equals("지우학")) {
                if (strings.length == 0) commandSender.sendMessage(index + "/지우학 시작 <학교탈출, 절비>");
                else if (strings[0].equals("시작")) {
                    if (strings.length == 1) commandSender.sendMessage(index + "게임을 선택해주세요.");
                    else if (strings[1].equals("학교탈출") || strings[1].equals("절비")) GameHandler.onCommand(commandSender, strings);
                    else commandSender.sendMessage(index + "없는 게임입니다.");
                }
            }
        }
        return false;
    }

    @Override
    public @org.jetbrains.annotations.Nullable List<String> onTabComplete(@org.jetbrains.annotations.NotNull CommandSender commandSender, @org.jetbrains.annotations.NotNull Command command, @org.jetbrains.annotations.NotNull String s, @org.jetbrains.annotations.NotNull String[] strings) {
        if (commandSender.isOp()) {
            if (s.equals("지우학")) {
                switch (strings.length) {
                    case 1: return Arrays.asList("시작");
                    case 2: return Arrays.asList("학교탈출", "절비");
                }
            }
        }
        return null;
    }
}
