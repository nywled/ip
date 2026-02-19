# AI Usage Documentation
This documentation serves as a compilation of how and where AI was used in this project.
## AI Tools used
- ChatGPT 5.2
# Usage Areas
## A-BetterGUI
### Scope
The AI was tasked with improving the GUI in terms of suggesting a colour scheme that fits the theme of
"having a hamster as a buddy" and the GUI layout.
### AI Contribution
A warm yellow theme was proposed to match the colour of yellow hamsters. Variants of blue was
proposed as the secondary colour.
* Input box: pale beige #F5EECB
* User bubble: light steel blue #A7C7E7
* Momo bubble: goldenrod #D9A441
* Enter button: steel blue #4595B5

A padding was proposed to be placed in the anchorPane.
`src/main/resources/css/main.css`
```css
.root {
    -fx-padding: 5;
}
```

## A-MoreErrorHandling
### Scope
Given the Parser, Storage and Command classes, the AI was tasked with finding out if any user inputs will
cause the software to break.
#### AI Contribution
Several flaws were found by AI just through one look at the code. The following inputs will cause the software to crash upon the next start up:
* any field inputs with "|"
* any field inputs with ","

The following solution was proposed and implemented: **Use character escaping**
1. Escape characters when saving.

In `src/main/java/momo/tasks/Tasks.java`, add the following:
```java
public class Task {
    ...
    private static final String ESC_BACKSLASH = "%5C";
    private static final String ESC_PIPE = "%7C";
    private static final String ESC_COMMA = "%2C";
    ...

    private String unescapeField(String s) {
        if (s == null) {
            return null;
        }
        // Reverse order of escapeField (backslash first)
        return s.replace(ESC_BACKSLASH, "\\")
                .replace(ESC_PIPE, "|")
                .replace(ESC_COMMA, ",");
    }
}
```
and modify the `toStorageString()`:
```java
public String toStorageString() {
    int status = isComplete ? COMPLETE_STATUS : INCOMPLETE_STATUS;
    String safeTitle = escapeField(this.title);

    String safeTags = tags.stream()
            .sorted()
            .map(this::escapeField)
            .collect(Collectors.joining(", "));

    return ("|" + status + "|" + safeTitle + "|" + safeTags);
}
```
2. Unescape characters when loading

In `src/main/java/momo/storage/Storage.java`, add the following:
```java
public class Storage {
    ...
    private static final String ESC_PIPE = "%7C";
    private static final String ESC_COMMA = "%2C";
    private static final String ESC_BACKSLASH = "%5C";
    ...

    private String unescapeField(String s) {
        if (s == null) {
            return null;
        }
        // Reverse order of escapeField (backslash first)
        return s.replace(ESC_BACKSLASH, "\\")
                .replace(ESC_PIPE, "|")
                .replace(ESC_COMMA, ",");
    }
}

and modify parseTask():
```java
private Task parseTask(String ptask) {
        ...
        String title = unescapeField(tokens[2]);

        ...

        addTagsToTask(task, unescapeField(tokens[3]));
        return task;
    }
```




