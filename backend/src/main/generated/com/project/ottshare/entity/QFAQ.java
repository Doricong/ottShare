package com.project.ottshare.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFAQ is a Querydsl query type for FAQ
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFAQ extends EntityPathBase<FAQ> {

    private static final long serialVersionUID = 1269818311L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFAQ fAQ = new QFAQ("fAQ");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath title = createString("title");

    public final QUser user;

    public final NumberPath<Integer> views = createNumber("views", Integer.class);

    public QFAQ(String variable) {
        this(FAQ.class, forVariable(variable), INITS);
    }

    public QFAQ(Path<? extends FAQ> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFAQ(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFAQ(PathMetadata metadata, PathInits inits) {
        this(FAQ.class, metadata, inits);
    }

    public QFAQ(Class<? extends FAQ> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

