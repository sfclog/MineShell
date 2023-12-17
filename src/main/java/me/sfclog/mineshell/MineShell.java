package me.sfclog.mineshell;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MineShell extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        getCommand("shell").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§7Sử dụng: /shell <lệnh>");
            return true;
        }
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(args);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            StringBuilder errors = new StringBuilder();
            while ((line = errorReader.readLine()) != null) {
                errors.append(line).append("\n");
            }
            reader.close();
            errorReader.close();
            if (errors.length() > 0) {
                sender.sendMessage("§cLỗi trong quá trình thực thi:\n" + errors.toString());
            } else {
                sender.sendMessage("§aKết quả lệnh:\n" + output.toString());
            }
        } catch (IOException e) {
            sender.sendMessage("§cĐã xảy ra lỗi khi thực thi lệnh.");
            e.printStackTrace();
        }
        return true;

 }
}
