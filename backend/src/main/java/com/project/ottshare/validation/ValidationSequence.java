package com.project.ottshare.validation;

import com.project.ottshare.validation.ValidationGroups.NotBlankGroups;
import com.project.ottshare.validation.ValidationGroups.PatternGroups;
import com.project.ottshare.validation.ValidationGroups.RangeGroups;
import jakarta.validation.GroupSequence;

@GroupSequence(value = {NotBlankGroups.class, RangeGroups.class, PatternGroups.class})
public interface ValidationSequence {
}
