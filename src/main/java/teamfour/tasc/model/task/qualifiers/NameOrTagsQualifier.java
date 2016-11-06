//@@author A0127014W

package teamfour.tasc.model.task.qualifiers;

import java.util.Set;

import teamfour.tasc.commons.util.StringUtil;
import teamfour.tasc.model.task.ReadOnlyTask;

/**
 * This qualifier allows tasks with names or tags
 * which matches the specified keywords to pass.
 */
public class NameOrTagsQualifier implements Qualifier {
    private Set<String> nameKeyWords;

    public NameOrTagsQualifier(Set<String> nameKeyWords) {
        this.nameKeyWords = nameKeyWords;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        return isNameFound(task) || isTagFound(task);
    }

    private boolean isNameFound(ReadOnlyTask task) {
        return nameKeyWords.stream()
                .filter(keyword -> StringUtil.containsIgnoreCasePartial(task.getName().getName(), keyword)).findAny()
                .isPresent();
    }

    private boolean isTagFound(ReadOnlyTask task) {
        boolean isFound = false;
        for (String keyword : nameKeyWords) {
            isFound = task.getTags().getInternalList().stream()
                    .filter(tag -> StringUtil.containsIgnoreCasePartial(tag.toString(), keyword)).findAny().isPresent();
            if (isFound) {
                return true;
            }
        }
        return isFound;
    }

    @Override
    public String toString() {
        return "name=" + String.join(", ", nameKeyWords);
    }
}