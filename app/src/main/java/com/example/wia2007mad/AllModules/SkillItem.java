package com.example.wia2007mad.AllModules;

public class SkillItem {
    String AuthorName;
    String SkillTitle;
    String SkillDescription;
    int SkillImage;

    public SkillItem(String authorName, String skillTitle, String skillDescription, int skillImage) {
        AuthorName = authorName;
        SkillTitle = skillTitle;
        SkillDescription = skillDescription;
        SkillImage = skillImage;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    public void setAuthorName(String authorName) {
        AuthorName = authorName;
    }

    public String getSkillTitle() {
        return SkillTitle;
    }

    public void setSkillTitle(String skillTitle) {
        SkillTitle = skillTitle;
    }

    public String getSkillDescription() {
        return SkillDescription;
    }

    public void setSkillDescription(String skillDescription) {
        SkillDescription = skillDescription;
    }

    public int getSkillImage() {
        return SkillImage;
    }

    public void setSkillImage(int skillImage) {
        SkillImage = skillImage;
    }
}
