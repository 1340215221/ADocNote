package com.rh.note.service;

import com.rh.note.ao.CreateProjectAO;
import com.rh.note.entity.adoc.impl.AdocProject;

/**
 * 处理adoc逻辑
 */
public interface IAdocService {
    AdocProject createProject(CreateProjectAO ao);
}
