package com.project.ottshare.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOttRecQuestions is a Querydsl query type for OttRecQuestions
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOttRecQuestions extends EntityPathBase<OttRecQuestions> {

    private static final long serialVersionUID = 1381816989L;

    public static final QOttRecQuestions ottRecQuestions = new QOttRecQuestions("ottRecQuestions");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath firstQuestion = createString("firstQuestion");

    public final EnumPath<com.project.ottshare.enums.OttType> firstQuestionOttType = createEnum("firstQuestionOttType", com.project.ottshare.enums.OttType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath secondQuestion = createString("secondQuestion");

    public final EnumPath<com.project.ottshare.enums.OttType> secondQuestionOttType = createEnum("secondQuestionOttType", com.project.ottshare.enums.OttType.class);

    public QOttRecQuestions(String variable) {
        super(OttRecQuestions.class, forVariable(variable));
    }

    public QOttRecQuestions(Path<? extends OttRecQuestions> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOttRecQuestions(PathMetadata metadata) {
        super(OttRecQuestions.class, metadata);
    }

}

