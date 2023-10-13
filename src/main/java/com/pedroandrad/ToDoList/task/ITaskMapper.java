package com.pedroandrad.ToDoList.task;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
@Mapper(componentModel = "spring")
public interface ITaskMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TaskModel updateTaskFromDTO(TaskDto taskDto, @MappingTarget TaskModel task);
}
