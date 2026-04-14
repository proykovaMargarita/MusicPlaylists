package bg.tu_varna.sit.f24621684.commands;

public interface Command {
    /** Изпълнява командата и извежда съобщенние за успех или грешка*/
    String execute(String[] args);
    /** Връша описанието на командата и параметрите, които тя изисква ако има такива */
    String getDescription();
}
