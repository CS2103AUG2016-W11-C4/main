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
    private Set<String> keyWords;

    public NameOrTagsQualifier(Set<String> keyWords) {
        this.keyWords = keyWords;
    }
    
    @Override
    public boolean run(ReadOnlyTask task) {
        boolean tagFound = false;
        for (String keyword : keyWords) {
            tagFound = task.getTags().getInternalList().stream()
                    .filter(tag -> StringUtil.containsIgnoreCasePartial(tag.toString(), keyword)).findAny()
                    .isPresent();
            if (tagFound) {
                return true;
            }
        }
        
        return keyWords.stream()
                .filter(keyword -> StringUtil.containsIgnoreCasePartial(task.getName().getName(), keyword))
                .findAny().isPresent();
    }
    
    @Override
    public String toString() {
        return "name or tags=" + String.join(", ", keyWords);
    }
}