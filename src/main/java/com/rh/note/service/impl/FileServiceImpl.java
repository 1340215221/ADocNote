package com.rh.note.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.rh.note.ao.CreateProjectAO;
import com.rh.note.entity.adoc.impl.AdocProject;
import com.rh.note.entity.adoc.impl.AdocReadMe;
import com.rh.note.service.IFileService;
import lombok.NonNull;
import org.apache.commons.lang3.mutable.MutableInt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * 文件服务
 */
public class FileServiceImpl implements IFileService {

    @Override
    public AdocProject createProject(@NonNull AdocProject adocProject) {
        // 项目目录
        adocProject.
        // 项目文件
    }

    private void writeFile(@NonNull String path, Supplier<byte[]> supplier) {
    }

    @Override
    public boolean checkProjectExist(@NonNull CreateProjectAO ao) {
        String path = ao.generateAbsolutePath();
        File file = new File(path);
        //项目目录存在
        if (!file.exists() || file.isFile()) {
            return false;
        }
        //readme.adoc文件存在
        String[] list = file.list();
        if (list.length < 1) {
            return false;
        }

        return ArrayUtil.isNotEmpty(file.list((dir, name) -> AdocReadMe.readMeFileName.equals(name)));
    }

    @Override
    public AdocProject readProjectFile(@NonNull CreateProjectAO ao) {
        String path = ao.generateAbsolutePath();
        BiConsumer<File, String> consumer = (file, line) -> {
        };
        this.readFiles(path, consumer);
    }

    /**
     * 读取目标文件
     *
     * readme文件
     * 标题文件 adoc/two_level
     * 内容文件 adoc/content
     */
    private void readFiles(@NonNull String path, @NonNull BiConsumer<File, String> consumer) {
        MutableInt readNum = new MutableInt(0);
        List<File> files = new ArrayList<>();
        int pageSize = 7;

        Supplier<File> findFileSupplier = () -> {
        };

        Stream.generate(findFileSupplier).

    }

}
