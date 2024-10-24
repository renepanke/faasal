# faasal

> faasal (FaaS Abstraction Layer) is a proof of concept of abstracting away the underlying cloud provider.

## What is not covered (yet?!) by this abstraction layer

- Uploading to the respective platform
- Trigger setup (AWS Gateway, HTTP Trigger for Azure & Google)
- Permissions
- Environment variables/secrets & other function specific settings
- Deploying
- Testing via HTTP


## Usage

You would implement just the `Function` abstract class like to be seen in [this test class.](https://github.com/renepanke/faasal/blob/master/src/test/java/io/github/renepanke/faasal/FunctionImplementationTest.java)

With the following platform specific commands jars are output that contain only the cloud-platform specific dependencies
and sourcecode so that it can be instantly uploaded to the cloud-platform.

This is achieved by using the following maven plugins and functions:
- [Maven Profiles](https://maven.apache.org/guides/introduction/introduction-to-profiles.html)
- [maven-shade-plugin](https://maven.apache.org/plugins/maven-shade-plugin/)
    - used to include the cloud-platform specific dependencies in the output jar & corresponding classes
- [maven-compiler-plugin](https://maven.apache.org/plugins/maven-compiler-plugin/)
    - used to include only the cloud-platform specific wrapper sourcecode

### Command

#### Windows

`.\build.ps1`

#### Linux / macOs

`./build.sh`

### Output

The execution of the commands outputs 3 files in the `/target` folder of your project:

1. faasal-1.0.0-aws-lambda-function.jar (16 KB)
2. faasal-1.0.0-azure-function.jar (53 KB)
3. faasal-1.0.0-gcp-function.jar (2299 KB) 
    - Here `com.fasterxml.jackson` is used for serialization and could be replaced with a more lightweight library like [gson from Google](https://github.com/google/gson) which has only a[ size of 291 KB in the current release](https://repo1.maven.org/maven2/com/google/code/gson/gson/2.11.0/) instead of the [1,6 MB jackson-databind comes with](https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.18.0/).

When extracting those jar containers they will contain the following files:

#### faasal-1.0.0-aws-lambda-function.jar

```
├── com
│   └── amazonaws
│       └── services
│           └── lambda
│               └── runtime
│                   ├── Client.class
│                   ├── ClientContext.class
│                   ├── CognitoIdentity.class
│                   ├── Context.class
│                   ├── CustomPojoSerializer.class
│                   ├── LambdaLogger.class
│                   ├── LambdaRuntime$1.class
│                   ├── LambdaRuntime.class
│                   ├── LambdaRuntimeInternal.class
│                   ├── logging
│                   │   ├── LogFormat.class
│                   │   └── LogLevel.class
│                   ├── RequestHandler.class
│                   └── RequestStreamHandler.class
├── io
│   └── github
│       └── renepanke
│           └── faasal
│               ├── api
│               │   ├── Function.class
│               │   ├── Logger.class
│               │   └── Result.class
│               └── aws
│                   ├── AwsLambdaDecorator.class
│                   └── AwsLambdaLogger.class
└── META-INF
    ├── MANIFEST.MF
    └── maven
        └── com.amazonaws
            └── aws-lambda-java-core
                ├── pom.properties
                └── pom.xml
```

#### faasal-1.0.0-azure-function

```
├── com
│   └── microsoft
│       └── azure
│           └── functions
│               ├── annotation
│               │   ├── AccessRights.class
│               │   ├── AuthorizationLevel.class
│               │   ├── BindingName.class
│               │   ├── BlobInput.class
│               │   ├── BlobOutput.class
│               │   ├── BlobTrigger.class
│               │   ├── Cardinality.class
│               │   ├── CosmosDBInput.class
│               │   ├── CosmosDBOutput.class
│               │   ├── CosmosDBTrigger.class
│               │   ├── CustomBinding.class
│               │   ├── EventGridOutput.class
│               │   ├── EventGridTrigger.class
│               │   ├── EventHubOutput.class
│               │   ├── EventHubTrigger.class
│               │   ├── ExponentialBackoffRetry.class
│               │   ├── FixedDelayRetry.class
│               │   ├── FunctionName.class
│               │   ├── HasImplicitOutput.class
│               │   ├── HttpOutput.class
│               │   ├── HttpTrigger.class
│               │   ├── KafkaOutput.class
│               │   ├── KafkaTrigger.class
│               │   ├── QueueOutput.class
│               │   ├── QueueTrigger.class
│               │   ├── SendGridOutput.class
│               │   ├── ServiceBusQueueOutput.class
│               │   ├── ServiceBusQueueTrigger.class
│               │   ├── ServiceBusTopicOutput.class
│               │   ├── ServiceBusTopicTrigger.class
│               │   ├── StorageAccount.class
│               │   ├── TableInput.class
│               │   ├── TableOutput.class
│               │   ├── TimerTrigger.class
│               │   ├── TwilioSmsOutput.class
│               │   └── WarmupTrigger.class
│               ├── BrokerAuthenticationMode.class
│               ├── BrokerProtocol.class
│               ├── ExecutionContext.class
│               ├── HttpMethod.class
│               ├── HttpRequestMessage.class
│               ├── HttpResponseMessage$Builder.class
│               ├── HttpResponseMessage.class
│               ├── HttpStatus.class
│               ├── HttpStatusType$1.class
│               ├── HttpStatusType.class
│               ├── OutputBinding.class
│               ├── RetryContext.class
│               ├── RpcException.class
│               └── TraceContext.class
├── io
│   └── github
│       └── renepanke
│           └── faasal
│               ├── api
│               │   ├── Function.class
│               │   ├── Logger.class
│               │   └── Result.class
│               └── azure
│                   ├── AzureFunctionDecorator.class
│                   └── AzureFunctionsLogger.class
└── META-INF
    ├── MANIFEST.MF
    ├── maven
    │   └── com.microsoft.azure.functions
    │       ├── azure-functions-java-core-library
    │       │   ├── pom.properties
    │       │   └── pom.xml
    │       └── azure-functions-java-library
    │           ├── pom.properties
    │           └── pom.xml
    ├── MSFTSIG.RSA
    └── MSFTSIG.SF
```

#### faasal-1.0.0-gcp-function

> Here `com.fasterxml.jackson` is used for serialization and could be replaced with a more lightweight library like [gson from Google](https://github.com/google/gson) which has only a[ size of 291 KB in the current release](https://repo1.maven.org/maven2/com/google/code/gson/gson/2.11.0/) instead of the [1,6 MB jackson-databind comes with](https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.18.0/).

```
├── com
│   ├── fasterxml
│   │   └── jackson
│   │       ├── annotation
│   │       │   ├── JacksonAnnotation.class
│   │       │   ├── JacksonAnnotationsInside.class
│   │       │   ├── JacksonAnnotationValue.class
│   │       │   ├── JacksonInject$Value.class
│   │       │   ├── JacksonInject.class
│   │       │   ├── JsonAlias.class
│   │       │   ├── JsonAnyGetter.class
│   │       │   ├── JsonAnySetter.class
│   │       │   ├── JsonAutoDetect$1.class
│   │       │   ├── JsonAutoDetect$Value.class
│   │       │   ├── JsonAutoDetect$Visibility.class
│   │       │   ├── JsonAutoDetect.class
│   │       │   ├── JsonBackReference.class
│   │       │   ├── JsonClassDescription.class
│   │       │   ├── JsonCreator$Mode.class
│   │       │   ├── JsonCreator.class
│   │       │   ├── JsonEnumDefaultValue.class
│   │       │   ├── JsonFilter.class
│   │       │   ├── JsonFormat$Feature.class
│   │       │   ├── JsonFormat$Features.class
│   │       │   ├── JsonFormat$Shape.class
│   │       │   ├── JsonFormat$Value.class
│   │       │   ├── JsonFormat.class
│   │       │   ├── JsonGetter.class
│   │       │   ├── JsonIdentityInfo.class
│   │       │   ├── JsonIdentityReference.class
│   │       │   ├── JsonIgnore.class
│   │       │   ├── JsonIgnoreProperties$Value.class
│   │       │   ├── JsonIgnoreProperties.class
│   │       │   ├── JsonIgnoreType.class
│   │       │   ├── JsonInclude$Include.class
│   │       │   ├── JsonInclude$Value.class
│   │       │   ├── JsonInclude.class
│   │       │   ├── JsonIncludeProperties$Value.class
│   │       │   ├── JsonIncludeProperties.class
│   │       │   ├── JsonKey.class
│   │       │   ├── JsonManagedReference.class
│   │       │   ├── JsonMerge.class
│   │       │   ├── JsonProperty$Access.class
│   │       │   ├── JsonProperty.class
│   │       │   ├── JsonPropertyDescription.class
│   │       │   ├── JsonPropertyOrder.class
│   │       │   ├── JsonRawValue.class
│   │       │   ├── JsonRootName.class
│   │       │   ├── JsonSetter$Value.class
│   │       │   ├── JsonSetter.class
│   │       │   ├── JsonSubTypes$Type.class
│   │       │   ├── JsonSubTypes.class
│   │       │   ├── JsonTypeId.class
│   │       │   ├── JsonTypeInfo$As.class
│   │       │   ├── JsonTypeInfo$Id.class
│   │       │   ├── JsonTypeInfo$None.class
│   │       │   ├── JsonTypeInfo$Value.class
│   │       │   ├── JsonTypeInfo.class
│   │       │   ├── JsonTypeName.class
│   │       │   ├── JsonUnwrapped.class
│   │       │   ├── JsonValue.class
│   │       │   ├── JsonView.class
│   │       │   ├── Nulls.class
│   │       │   ├── ObjectIdGenerator$IdKey.class
│   │       │   ├── ObjectIdGenerator.class
│   │       │   ├── ObjectIdGenerators$Base.class
│   │       │   ├── ObjectIdGenerators$IntSequenceGenerator.class
│   │       │   ├── ObjectIdGenerators$None.class
│   │       │   ├── ObjectIdGenerators$PropertyGenerator.class
│   │       │   ├── ObjectIdGenerators$StringIdGenerator.class
│   │       │   ├── ObjectIdGenerators$UUIDGenerator.class
│   │       │   ├── ObjectIdGenerators.class
│   │       │   ├── ObjectIdResolver.class
│   │       │   ├── OptBoolean.class
│   │       │   ├── package-info.class
│   │       │   ├── PropertyAccessor.class
│   │       │   └── SimpleObjectIdResolver.class
│   │       ├── core
│   │       │   ├── async
│   │       │   │   ├── ByteArrayFeeder.class
│   │       │   │   ├── ByteBufferFeeder.class
│   │       │   │   ├── NonBlockingInputFeeder.class
│   │       │   │   └── package-info.class
│   │       │   ├── base
│   │       │   │   ├── GeneratorBase.class
│   │       │   │   ├── package-info.class
│   │       │   │   ├── ParserBase.class
│   │       │   │   └── ParserMinimalBase.class
│   │       │   ├── Base64Variant$PaddingReadBehaviour.class
│   │       │   ├── Base64Variant.class
│   │       │   ├── Base64Variants.class
│   │       │   ├── ErrorReportConfiguration$Builder.class
│   │       │   ├── ErrorReportConfiguration.class
│   │       │   ├── exc
│   │       │   │   ├── InputCoercionException.class
│   │       │   │   ├── package-info.class
│   │       │   │   ├── StreamConstraintsException.class
│   │       │   │   ├── StreamReadException.class
│   │       │   │   └── StreamWriteException.class
│   │       │   ├── filter
│   │       │   │   ├── FilteringGeneratorDelegate.class
│   │       │   │   ├── FilteringParserDelegate.class
│   │       │   │   ├── JsonPointerBasedFilter.class
│   │       │   │   ├── TokenFilter$Inclusion.class
│   │       │   │   ├── TokenFilter.class
│   │       │   │   └── TokenFilterContext.class
│   │       │   ├── format
│   │       │   │   ├── DataFormatDetector.class
│   │       │   │   ├── DataFormatMatcher.class
│   │       │   │   ├── InputAccessor$Std.class
│   │       │   │   ├── InputAccessor.class
│   │       │   │   ├── MatchStrength.class
│   │       │   │   └── package-info.class
│   │       │   ├── FormatFeature.class
│   │       │   ├── FormatSchema.class
│   │       │   ├── internal
│   │       │   │   └── shaded
│   │       │   │       └── fdp
│   │       │   │           └── v2_18_0
│   │       │   │               ├── AbstractBigDecimalParser.class
│   │       │   │               ├── AbstractBigIntegerParser.class
│   │       │   │               ├── AbstractFloatValueParser.class
│   │       │   │               ├── AbstractJavaFloatingPointBitsFromByteArray.class
│   │       │   │               ├── AbstractJavaFloatingPointBitsFromCharArray.class
│   │       │   │               ├── AbstractJavaFloatingPointBitsFromCharSequence.class
│   │       │   │               ├── AbstractNumberParser.class
│   │       │   │               ├── BigSignificand.class
│   │       │   │               ├── FastDoubleMath.class
│   │       │   │               ├── FastDoubleSwar.class
│   │       │   │               ├── FastFloatMath.class
│   │       │   │               ├── FastIntegerMath.class
│   │       │   │               ├── FftMultiplier$ComplexVector.class
│   │       │   │               ├── FftMultiplier$MutableComplex.class
│   │       │   │               ├── FftMultiplier.class
│   │       │   │               ├── JavaBigDecimalFromByteArray.class
│   │       │   │               ├── JavaBigDecimalFromCharArray.class
│   │       │   │               ├── JavaBigDecimalFromCharSequence.class
│   │       │   │               ├── JavaBigDecimalParser.class
│   │       │   │               ├── JavaBigIntegerFromByteArray.class
│   │       │   │               ├── JavaBigIntegerFromCharArray.class
│   │       │   │               ├── JavaBigIntegerFromCharSequence.class
│   │       │   │               ├── JavaBigIntegerParser.class
│   │       │   │               ├── JavaDoubleBitsFromByteArray.class
│   │       │   │               ├── JavaDoubleBitsFromCharArray.class
│   │       │   │               ├── JavaDoubleBitsFromCharSequence.class
│   │       │   │               ├── JavaDoubleParser.class
│   │       │   │               ├── JavaFloatBitsFromByteArray.class
│   │       │   │               ├── JavaFloatBitsFromCharArray.class
│   │       │   │               ├── JavaFloatBitsFromCharSequence.class
│   │       │   │               ├── JavaFloatParser.class
│   │       │   │               ├── package-info.class
│   │       │   │               ├── ParseDigitsTaskByteArray.class
│   │       │   │               ├── ParseDigitsTaskCharArray.class
│   │       │   │               └── ParseDigitsTaskCharSequence.class
│   │       │   ├── io
│   │       │   │   ├── BigDecimalParser.class
│   │       │   │   ├── BigIntegerParser.class
│   │       │   │   ├── CharacterEscapes.class
│   │       │   │   ├── CharTypes$AltEscapes.class
│   │       │   │   ├── CharTypes.class
│   │       │   │   ├── ContentReference.class
│   │       │   │   ├── DataOutputAsStream.class
│   │       │   │   ├── InputDecorator.class
│   │       │   │   ├── IOContext.class
│   │       │   │   ├── JsonEOFException.class
│   │       │   │   ├── JsonStringEncoder.class
│   │       │   │   ├── MergedStream.class
│   │       │   │   ├── NumberInput.class
│   │       │   │   ├── NumberOutput.class
│   │       │   │   ├── OutputDecorator.class
│   │       │   │   ├── schubfach
│   │       │   │   │   ├── DoubleToDecimal.class
│   │       │   │   │   ├── FloatToDecimal.class
│   │       │   │   │   └── MathUtils.class
│   │       │   │   ├── SegmentedStringWriter.class
│   │       │   │   ├── SerializedString.class
│   │       │   │   ├── UTF32Reader.class
│   │       │   │   └── UTF8Writer.class
│   │       │   ├── JacksonException.class
│   │       │   ├── json
│   │       │   │   ├── async
│   │       │   │   │   ├── NonBlockingByteBufferJsonParser.class
│   │       │   │   │   ├── NonBlockingJsonParserBase.class
│   │       │   │   │   ├── NonBlockingJsonParser.class
│   │       │   │   │   ├── NonBlockingUtf8JsonParserBase.class
│   │       │   │   │   └── package-info.class
│   │       │   │   ├── ByteSourceJsonBootstrapper.class
│   │       │   │   ├── DupDetector.class
│   │       │   │   ├── JsonGeneratorImpl.class
│   │       │   │   ├── JsonParserBase.class
│   │       │   │   ├── JsonReadContext.class
│   │       │   │   ├── JsonReadFeature.class
│   │       │   │   ├── JsonWriteContext.class
│   │       │   │   ├── JsonWriteFeature.class
│   │       │   │   ├── package-info.class
│   │       │   │   ├── PackageVersion.class
│   │       │   │   ├── ReaderBasedJsonParser.class
│   │       │   │   ├── UTF8DataInputJsonParser.class
│   │       │   │   ├── UTF8JsonGenerator.class
│   │       │   │   ├── UTF8StreamJsonParser.class
│   │       │   │   └── WriterBasedJsonGenerator.class
│   │       │   ├── JsonEncoding.class
│   │       │   ├── JsonFactory$Feature.class
│   │       │   ├── JsonFactoryBuilder.class
│   │       │   ├── JsonFactory.class
│   │       │   ├── JsonGenerationException.class
│   │       │   ├── JsonGenerator$1.class
│   │       │   ├── JsonGenerator$Feature.class
│   │       │   ├── JsonGenerator.class
│   │       │   ├── JsonLocation.class
│   │       │   ├── JsonParseException.class
│   │       │   ├── JsonParser$Feature.class
│   │       │   ├── JsonParser$NumberType.class
│   │       │   ├── JsonParser$NumberTypeFP.class
│   │       │   ├── JsonParser.class
│   │       │   ├── JsonpCharacterEscapes.class
│   │       │   ├── JsonPointer$PointerParent.class
│   │       │   ├── JsonPointer$PointerSegment.class
│   │       │   ├── JsonPointer$Serialization.class
│   │       │   ├── JsonPointer.class
│   │       │   ├── JsonProcessingException.class
│   │       │   ├── JsonStreamContext.class
│   │       │   ├── JsonToken$1.class
│   │       │   ├── JsonToken.class
│   │       │   ├── JsonTokenId.class
│   │       │   ├── ObjectCodec.class
│   │       │   ├── package-info.class
│   │       │   ├── PrettyPrinter.class
│   │       │   ├── SerializableString.class
│   │       │   ├── StreamReadCapability.class
│   │       │   ├── StreamReadConstraints$Builder.class
│   │       │   ├── StreamReadConstraints.class
│   │       │   ├── StreamReadFeature.class
│   │       │   ├── StreamWriteCapability.class
│   │       │   ├── StreamWriteConstraints$Builder.class
│   │       │   ├── StreamWriteConstraints.class
│   │       │   ├── StreamWriteFeature.class
│   │       │   ├── sym
│   │       │   │   ├── ByteQuadsCanonicalizer$TableInfo.class
│   │       │   │   ├── ByteQuadsCanonicalizer.class
│   │       │   │   ├── CharsToNameCanonicalizer$Bucket.class
│   │       │   │   ├── CharsToNameCanonicalizer$TableInfo.class
│   │       │   │   ├── CharsToNameCanonicalizer.class
│   │       │   │   ├── Name1.class
│   │       │   │   ├── Name2.class
│   │       │   │   ├── Name3.class
│   │       │   │   ├── Name.class
│   │       │   │   ├── NameN.class
│   │       │   │   └── package-info.class
│   │       │   ├── TokenStreamFactory.class
│   │       │   ├── TreeCodec.class
│   │       │   ├── TreeNode.class
│   │       │   ├── TSFBuilder.class
│   │       │   ├── type
│   │       │   │   ├── package-info.class
│   │       │   │   ├── ResolvedType.class
│   │       │   │   ├── TypeReference.class
│   │       │   │   ├── WritableTypeId$Inclusion.class
│   │       │   │   └── WritableTypeId.class
│   │       │   ├── util
│   │       │   │   ├── BufferRecycler$Gettable.class
│   │       │   │   ├── BufferRecycler.class
│   │       │   │   ├── BufferRecyclers.class
│   │       │   │   ├── ByteArrayBuilder.class
│   │       │   │   ├── DefaultIndenter.class
│   │       │   │   ├── DefaultPrettyPrinter$FixedSpaceIndenter.class
│   │       │   │   ├── DefaultPrettyPrinter$Indenter.class
│   │       │   │   ├── DefaultPrettyPrinter$NopIndenter.class
│   │       │   │   ├── DefaultPrettyPrinter.class
│   │       │   │   ├── Instantiatable.class
│   │       │   │   ├── InternCache.class
│   │       │   │   ├── JacksonFeature.class
│   │       │   │   ├── JacksonFeatureSet.class
│   │       │   │   ├── JsonGeneratorDecorator.class
│   │       │   │   ├── JsonGeneratorDelegate.class
│   │       │   │   ├── JsonParserDelegate.class
│   │       │   │   ├── JsonParserSequence.class
│   │       │   │   ├── JsonRecyclerPools$BoundedPool.class
│   │       │   │   ├── JsonRecyclerPools$ConcurrentDequePool.class
│   │       │   │   ├── JsonRecyclerPools$LockFreePool.class
│   │       │   │   ├── JsonRecyclerPools$NonRecyclingPool.class
│   │       │   │   ├── JsonRecyclerPools$ThreadLocalPool.class
│   │       │   │   ├── JsonRecyclerPools.class
│   │       │   │   ├── MinimalPrettyPrinter.class
│   │       │   │   ├── package-info.class
│   │       │   │   ├── ReadConstrainedTextBuffer.class
│   │       │   │   ├── RecyclerPool$BoundedPoolBase.class
│   │       │   │   ├── RecyclerPool$ConcurrentDequePoolBase.class
│   │       │   │   ├── RecyclerPool$LockFreePoolBase$Node.class
│   │       │   │   ├── RecyclerPool$LockFreePoolBase.class
│   │       │   │   ├── RecyclerPool$NonRecyclingPoolBase.class
│   │       │   │   ├── RecyclerPool$StatefulImplBase.class
│   │       │   │   ├── RecyclerPool$ThreadLocalPoolBase.class
│   │       │   │   ├── RecyclerPool$WithPool.class
│   │       │   │   ├── RecyclerPool.class
│   │       │   │   ├── RequestPayload.class
│   │       │   │   ├── Separators$Spacing.class
│   │       │   │   ├── Separators.class
│   │       │   │   ├── TextBuffer.class
│   │       │   │   ├── ThreadLocalBufferManager$ThreadLocalBufferManagerHolder.class
│   │       │   │   ├── ThreadLocalBufferManager.class
│   │       │   │   └── VersionUtil.class
│   │       │   ├── Version.class
│   │       │   └── Versioned.class
│   │       └── databind
│   │           ├── AbstractTypeResolver.class
│   │           ├── annotation
│   │           │   ├── EnumNaming.class
│   │           │   ├── JacksonStdImpl.class
│   │           │   ├── JsonAppend$Attr.class
│   │           │   ├── JsonAppend$Prop.class
│   │           │   ├── JsonAppend.class
│   │           │   ├── JsonDeserialize.class
│   │           │   ├── JsonNaming.class
│   │           │   ├── JsonPOJOBuilder$Value.class
│   │           │   ├── JsonPOJOBuilder.class
│   │           │   ├── JsonSerialize$Inclusion.class
│   │           │   ├── JsonSerialize$Typing.class
│   │           │   ├── JsonSerialize.class
│   │           │   ├── JsonTypeIdResolver.class
│   │           │   ├── JsonTypeResolver.class
│   │           │   ├── JsonValueInstantiator.class
│   │           │   ├── NoClass.class
│   │           │   └── package-info.class
│   │           ├── AnnotationIntrospector$ReferenceProperty$Type.class
│   │           ├── AnnotationIntrospector$ReferenceProperty.class
│   │           ├── AnnotationIntrospector$XmlExtensions.class
│   │           ├── AnnotationIntrospector.class
│   │           ├── BeanDescription.class
│   │           ├── BeanProperty$Bogus.class
│   │           ├── BeanProperty$Std.class
│   │           ├── BeanProperty.class
│   │           ├── cfg
│   │           │   ├── BaseSettings.class
│   │           │   ├── CacheProvider.class
│   │           │   ├── CoercionAction.class
│   │           │   ├── CoercionConfig.class
│   │           │   ├── CoercionConfigs$1.class
│   │           │   ├── CoercionConfigs.class
│   │           │   ├── CoercionInputShape.class
│   │           │   ├── ConfigFeature.class
│   │           │   ├── ConfigOverride$Empty.class
│   │           │   ├── ConfigOverride.class
│   │           │   ├── ConfigOverrides.class
│   │           │   ├── ConstructorDetector$SingleArgConstructor.class
│   │           │   ├── ConstructorDetector.class
│   │           │   ├── ContextAttributes$Impl.class
│   │           │   ├── ContextAttributes.class
│   │           │   ├── DatatypeFeature.class
│   │           │   ├── DatatypeFeatures$DefaultHolder.class
│   │           │   ├── DatatypeFeatures.class
│   │           │   ├── DefaultCacheProvider$Builder.class
│   │           │   ├── DefaultCacheProvider.class
│   │           │   ├── DeserializerFactoryConfig.class
│   │           │   ├── EnumFeature.class
│   │           │   ├── HandlerInstantiator.class
│   │           │   ├── JsonNodeFeature.class
│   │           │   ├── MapperBuilder$1.class
│   │           │   ├── MapperBuilder.class
│   │           │   ├── MapperConfigBase.class
│   │           │   ├── MapperConfig.class
│   │           │   ├── MutableCoercionConfig.class
│   │           │   ├── MutableConfigOverride.class
│   │           │   ├── package-info.class
│   │           │   ├── PackageVersion.class
│   │           │   └── SerializerFactoryConfig.class
│   │           ├── DatabindContext.class
│   │           ├── DatabindException.class
│   │           ├── deser
│   │           │   ├── AbstractDeserializer.class
│   │           │   ├── BasicDeserializerFactory$ContainerDefaultMappings.class
│   │           │   ├── BasicDeserializerFactory.class
│   │           │   ├── BeanDeserializer$1.class
│   │           │   ├── BeanDeserializer$BeanReferring.class
│   │           │   ├── BeanDeserializerBase.class
│   │           │   ├── BeanDeserializerBuilder.class
│   │           │   ├── BeanDeserializer.class
│   │           │   ├── BeanDeserializerFactory.class
│   │           │   ├── BeanDeserializerModifier.class
│   │           │   ├── BuilderBasedDeserializer$1.class
│   │           │   ├── BuilderBasedDeserializer.class
│   │           │   ├── ContextualDeserializer.class
│   │           │   ├── ContextualKeyDeserializer.class
│   │           │   ├── CreatorProperty.class
│   │           │   ├── DataFormatReaders$AccessorForReader.class
│   │           │   ├── DataFormatReaders$Match.class
│   │           │   ├── DataFormatReaders.class
│   │           │   ├── DefaultDeserializationContext$Impl.class
│   │           │   ├── DefaultDeserializationContext.class
│   │           │   ├── DeserializationProblemHandler.class
│   │           │   ├── DeserializerCache.class
│   │           │   ├── DeserializerFactory.class
│   │           │   ├── Deserializers$Base.class
│   │           │   ├── Deserializers.class
│   │           │   ├── impl
│   │           │   │   ├── BeanAsArrayBuilderDeserializer.class
│   │           │   │   ├── BeanAsArrayDeserializer.class
│   │           │   │   ├── BeanPropertyMap.class
│   │           │   │   ├── CreatorCandidate$Param.class
│   │           │   │   ├── CreatorCandidate.class
│   │           │   │   ├── CreatorCollector.class
│   │           │   │   ├── ErrorThrowingDeserializer.class
│   │           │   │   ├── ExternalTypeHandler$Builder.class
│   │           │   │   ├── ExternalTypeHandler$ExtTypedProperty.class
│   │           │   │   ├── ExternalTypeHandler.class
│   │           │   │   ├── FailingDeserializer.class
│   │           │   │   ├── FieldProperty.class
│   │           │   │   ├── InnerClassProperty.class
│   │           │   │   ├── JavaUtilCollectionsDeserializers$JavaUtilCollectionsConverter.class
│   │           │   │   ├── JavaUtilCollectionsDeserializers.class
│   │           │   │   ├── JDKValueInstantiators$ArrayListInstantiator.class
│   │           │   │   ├── JDKValueInstantiators$ConcurrentHashMapInstantiator.class
│   │           │   │   ├── JDKValueInstantiators$ConstantValueInstantiator.class
│   │           │   │   ├── JDKValueInstantiators$HashMapInstantiator.class
│   │           │   │   ├── JDKValueInstantiators$HashSetInstantiator.class
│   │           │   │   ├── JDKValueInstantiators$JDKValueInstantiator.class
│   │           │   │   ├── JDKValueInstantiators$LinkedHashMapInstantiator.class
│   │           │   │   ├── JDKValueInstantiators$LinkedListInstantiator.class
│   │           │   │   ├── JDKValueInstantiators$TreeMapInstantiator.class
│   │           │   │   ├── JDKValueInstantiators$TreeSetInstantiator.class
│   │           │   │   ├── JDKValueInstantiators.class
│   │           │   │   ├── ManagedReferenceProperty.class
│   │           │   │   ├── MergingSettableBeanProperty.class
│   │           │   │   ├── MethodProperty.class
│   │           │   │   ├── NullsAsEmptyProvider.class
│   │           │   │   ├── NullsConstantProvider.class
│   │           │   │   ├── NullsFailProvider.class
│   │           │   │   ├── ObjectIdReader.class
│   │           │   │   ├── ObjectIdReferenceProperty$PropertyReferring.class
│   │           │   │   ├── ObjectIdReferenceProperty.class
│   │           │   │   ├── ObjectIdValueProperty.class
│   │           │   │   ├── package-info.class
│   │           │   │   ├── PropertyBasedCreator$CaseInsensitiveMap.class
│   │           │   │   ├── PropertyBasedCreator.class
│   │           │   │   ├── PropertyBasedObjectIdGenerator.class
│   │           │   │   ├── PropertyValue$Any.class
│   │           │   │   ├── PropertyValue$AnyParameter.class
│   │           │   │   ├── PropertyValue$Map.class
│   │           │   │   ├── PropertyValue$Regular.class
│   │           │   │   ├── PropertyValueBuffer.class
│   │           │   │   ├── PropertyValue.class
│   │           │   │   ├── ReadableObjectId$Referring.class
│   │           │   │   ├── ReadableObjectId.class
│   │           │   │   ├── SetterlessProperty.class
│   │           │   │   ├── TypeWrappedDeserializer.class
│   │           │   │   ├── UnsupportedTypeDeserializer.class
│   │           │   │   ├── UnwrappedPropertyHandler.class
│   │           │   │   └── ValueInjector.class
│   │           │   ├── KeyDeserializers.class
│   │           │   ├── NullValueProvider.class
│   │           │   ├── package-info.class
│   │           │   ├── ResolvableDeserializer.class
│   │           │   ├── SettableAnyProperty$AnySetterReferring.class
│   │           │   ├── SettableAnyProperty$JsonNodeFieldAnyProperty.class
│   │           │   ├── SettableAnyProperty$JsonNodeParameterAnyProperty.class
│   │           │   ├── SettableAnyProperty$MapFieldAnyProperty.class
│   │           │   ├── SettableAnyProperty$MapParameterAnyProperty.class
│   │           │   ├── SettableAnyProperty$MethodAnyProperty.class
│   │           │   ├── SettableAnyProperty.class
│   │           │   ├── SettableBeanProperty$Delegating.class
│   │           │   ├── SettableBeanProperty.class
│   │           │   ├── std
│   │           │   │   ├── ArrayBlockingQueueDeserializer.class
│   │           │   │   ├── AtomicBooleanDeserializer.class
│   │           │   │   ├── AtomicIntegerDeserializer.class
│   │           │   │   ├── AtomicLongDeserializer.class
│   │           │   │   ├── AtomicReferenceDeserializer.class
│   │           │   │   ├── BaseNodeDeserializer$ContainerStack.class
│   │           │   │   ├── BaseNodeDeserializer.class
│   │           │   │   ├── ByteBufferDeserializer.class
│   │           │   │   ├── CollectionDeserializer$CollectionReferringAccumulator.class
│   │           │   │   ├── CollectionDeserializer$CollectionReferring.class
│   │           │   │   ├── CollectionDeserializer.class
│   │           │   │   ├── ContainerDeserializerBase.class
│   │           │   │   ├── DateDeserializers$1.class
│   │           │   │   ├── DateDeserializers$CalendarDeserializer.class
│   │           │   │   ├── DateDeserializers$DateBasedDeserializer.class
│   │           │   │   ├── DateDeserializers$DateDeserializer.class
│   │           │   │   ├── DateDeserializers$SqlDateDeserializer.class
│   │           │   │   ├── DateDeserializers$TimestampDeserializer.class
│   │           │   │   ├── DateDeserializers.class
│   │           │   │   ├── DelegatingDeserializer.class
│   │           │   │   ├── EnumDeserializer$1.class
│   │           │   │   ├── EnumDeserializer.class
│   │           │   │   ├── EnumMapDeserializer.class
│   │           │   │   ├── EnumSetDeserializer.class
│   │           │   │   ├── FactoryBasedEnumDeserializer.class
│   │           │   │   ├── FromStringDeserializer$Std.class
│   │           │   │   ├── FromStringDeserializer$StringBufferDeserializer.class
│   │           │   │   ├── FromStringDeserializer$StringBuilderDeserializer.class
│   │           │   │   ├── FromStringDeserializer.class
│   │           │   │   ├── JdkDeserializers.class
│   │           │   │   ├── JsonLocationInstantiator.class
│   │           │   │   ├── JsonNodeDeserializer$ArrayDeserializer.class
│   │           │   │   ├── JsonNodeDeserializer$ObjectDeserializer.class
│   │           │   │   ├── JsonNodeDeserializer.class
│   │           │   │   ├── MapDeserializer$MapReferringAccumulator.class
│   │           │   │   ├── MapDeserializer$MapReferring.class
│   │           │   │   ├── MapDeserializer.class
│   │           │   │   ├── MapEntryDeserializer.class
│   │           │   │   ├── NullifyingDeserializer.class
│   │           │   │   ├── NumberDeserializers$1.class
│   │           │   │   ├── NumberDeserializers$BigDecimalDeserializer.class
│   │           │   │   ├── NumberDeserializers$BigIntegerDeserializer.class
│   │           │   │   ├── NumberDeserializers$BooleanDeserializer.class
│   │           │   │   ├── NumberDeserializers$ByteDeserializer.class
│   │           │   │   ├── NumberDeserializers$CharacterDeserializer.class
│   │           │   │   ├── NumberDeserializers$DoubleDeserializer.class
│   │           │   │   ├── NumberDeserializers$FloatDeserializer.class
│   │           │   │   ├── NumberDeserializers$IntegerDeserializer.class
│   │           │   │   ├── NumberDeserializers$LongDeserializer.class
│   │           │   │   ├── NumberDeserializers$NumberDeserializer.class
│   │           │   │   ├── NumberDeserializers$PrimitiveOrWrapperDeserializer.class
│   │           │   │   ├── NumberDeserializers$ShortDeserializer.class
│   │           │   │   ├── NumberDeserializers.class
│   │           │   │   ├── ObjectArrayDeserializer.class
│   │           │   │   ├── package-info.class
│   │           │   │   ├── PrimitiveArrayDeserializers$BooleanDeser.class
│   │           │   │   ├── PrimitiveArrayDeserializers$ByteDeser.class
│   │           │   │   ├── PrimitiveArrayDeserializers$CharDeser.class
│   │           │   │   ├── PrimitiveArrayDeserializers$DoubleDeser.class
│   │           │   │   ├── PrimitiveArrayDeserializers$FloatDeser.class
│   │           │   │   ├── PrimitiveArrayDeserializers$IntDeser.class
│   │           │   │   ├── PrimitiveArrayDeserializers$LongDeser.class
│   │           │   │   ├── PrimitiveArrayDeserializers$ShortDeser.class
│   │           │   │   ├── PrimitiveArrayDeserializers.class
│   │           │   │   ├── ReferenceTypeDeserializer.class
│   │           │   │   ├── StackTraceElementDeserializer$Adapter.class
│   │           │   │   ├── StackTraceElementDeserializer.class
│   │           │   │   ├── StdDelegatingDeserializer.class
│   │           │   │   ├── StdDeserializer$1.class
│   │           │   │   ├── StdDeserializer.class
│   │           │   │   ├── StdKeyDeserializer$DelegatingKD.class
│   │           │   │   ├── StdKeyDeserializer$EnumKD.class
│   │           │   │   ├── StdKeyDeserializer$StringCtorKeyDeserializer.class
│   │           │   │   ├── StdKeyDeserializer$StringFactoryKeyDeserializer.class
│   │           │   │   ├── StdKeyDeserializer$StringKD.class
│   │           │   │   ├── StdKeyDeserializer.class
│   │           │   │   ├── StdKeyDeserializers.class
│   │           │   │   ├── StdNodeBasedDeserializer.class
│   │           │   │   ├── StdScalarDeserializer.class
│   │           │   │   ├── StdValueInstantiator.class
│   │           │   │   ├── StringArrayDeserializer.class
│   │           │   │   ├── StringCollectionDeserializer.class
│   │           │   │   ├── StringDeserializer.class
│   │           │   │   ├── ThrowableDeserializer.class
│   │           │   │   ├── TokenBufferDeserializer.class
│   │           │   │   ├── UntypedObjectDeserializer$Vanilla.class
│   │           │   │   ├── UntypedObjectDeserializer.class
│   │           │   │   ├── UntypedObjectDeserializerNR$Scope.class
│   │           │   │   ├── UntypedObjectDeserializerNR.class
│   │           │   │   └── UUIDDeserializer.class
│   │           │   ├── UnresolvedForwardReference.class
│   │           │   ├── UnresolvedId.class
│   │           │   ├── ValueInstantiator$Base.class
│   │           │   ├── ValueInstantiator$Delegating.class
│   │           │   ├── ValueInstantiator$Gettable.class
│   │           │   ├── ValueInstantiator.class
│   │           │   ├── ValueInstantiators$Base.class
│   │           │   └── ValueInstantiators.class
│   │           ├── DeserializationConfig.class
│   │           ├── DeserializationContext.class
│   │           ├── DeserializationFeature.class
│   │           ├── EnumNamingStrategies$CamelCaseStrategy.class
│   │           ├── EnumNamingStrategies.class
│   │           ├── EnumNamingStrategy.class
│   │           ├── exc
│   │           │   ├── IgnoredPropertyException.class
│   │           │   ├── InvalidDefinitionException.class
│   │           │   ├── InvalidFormatException.class
│   │           │   ├── InvalidNullException.class
│   │           │   ├── InvalidTypeIdException.class
│   │           │   ├── MismatchedInputException.class
│   │           │   ├── PropertyBindingException.class
│   │           │   ├── UnrecognizedPropertyException.class
│   │           │   └── ValueInstantiationException.class
│   │           ├── ext
│   │           │   ├── CoreXMLDeserializers$Std.class
│   │           │   ├── CoreXMLDeserializers.class
│   │           │   ├── CoreXMLSerializers$XMLGregorianCalendarSerializer.class
│   │           │   ├── CoreXMLSerializers.class
│   │           │   ├── DOMDeserializer$DocumentDeserializer.class
│   │           │   ├── DOMDeserializer$NodeDeserializer.class
│   │           │   ├── DOMDeserializer.class
│   │           │   ├── DOMSerializer.class
│   │           │   ├── Java7Handlers.class
│   │           │   ├── Java7HandlersImpl.class
│   │           │   ├── Java7Support.class
│   │           │   ├── Java7SupportImpl.class
│   │           │   ├── NioPathDeserializer.class
│   │           │   ├── NioPathSerializer.class
│   │           │   ├── OptionalHandlerFactory.class
│   │           │   ├── package-info.class
│   │           │   └── SqlBlobSerializer.class
│   │           ├── InjectableValues$Std.class
│   │           ├── InjectableValues.class
│   │           ├── introspect
│   │           │   ├── AccessorNamingStrategy$Base.class
│   │           │   ├── AccessorNamingStrategy$Provider.class
│   │           │   ├── AccessorNamingStrategy.class
│   │           │   ├── AnnotatedAndMetadata.class
│   │           │   ├── Annotated.class
│   │           │   ├── AnnotatedClass$Creators.class
│   │           │   ├── AnnotatedClass.class
│   │           │   ├── AnnotatedClassResolver.class
│   │           │   ├── AnnotatedConstructor$Serialization.class
│   │           │   ├── AnnotatedConstructor.class
│   │           │   ├── AnnotatedCreatorCollector.class
│   │           │   ├── AnnotatedField$Serialization.class
│   │           │   ├── AnnotatedField.class
│   │           │   ├── AnnotatedFieldCollector$FieldBuilder.class
│   │           │   ├── AnnotatedFieldCollector.class
│   │           │   ├── AnnotatedMember.class
│   │           │   ├── AnnotatedMethod$Serialization.class
│   │           │   ├── AnnotatedMethod.class
│   │           │   ├── AnnotatedMethodCollector$MethodBuilder.class
│   │           │   ├── AnnotatedMethodCollector.class
│   │           │   ├── AnnotatedMethodMap.class
│   │           │   ├── AnnotatedParameter.class
│   │           │   ├── AnnotatedWithParams.class
│   │           │   ├── AnnotationCollector$EmptyCollector.class
│   │           │   ├── AnnotationCollector$NCollector.class
│   │           │   ├── AnnotationCollector$NoAnnotations.class
│   │           │   ├── AnnotationCollector$OneAnnotation.class
│   │           │   ├── AnnotationCollector$OneCollector.class
│   │           │   ├── AnnotationCollector$TwoAnnotations.class
│   │           │   ├── AnnotationCollector.class
│   │           │   ├── AnnotationIntrospectorPair.class
│   │           │   ├── AnnotationMap.class
│   │           │   ├── BasicBeanDescription.class
│   │           │   ├── BasicClassIntrospector.class
│   │           │   ├── BeanPropertyDefinition.class
│   │           │   ├── ClassIntrospector$MixInResolver.class
│   │           │   ├── ClassIntrospector.class
│   │           │   ├── CollectorBase.class
│   │           │   ├── ConcreteBeanPropertyBase.class
│   │           │   ├── DefaultAccessorNamingStrategy$BaseNameValidator.class
│   │           │   ├── DefaultAccessorNamingStrategy$FirstCharBasedValidator.class
│   │           │   ├── DefaultAccessorNamingStrategy$Provider.class
│   │           │   ├── DefaultAccessorNamingStrategy$RecordNaming.class
│   │           │   ├── DefaultAccessorNamingStrategy.class
│   │           │   ├── EnumNamingStrategyFactory.class
│   │           │   ├── JacksonAnnotationIntrospector$1.class
│   │           │   ├── JacksonAnnotationIntrospector.class
│   │           │   ├── MemberKey.class
│   │           │   ├── MethodGenericTypeResolver.class
│   │           │   ├── NopAnnotationIntrospector$1.class
│   │           │   ├── NopAnnotationIntrospector.class
│   │           │   ├── ObjectIdInfo.class
│   │           │   ├── package-info.class
│   │           │   ├── POJOPropertiesCollector$1.class
│   │           │   ├── POJOPropertiesCollector.class
│   │           │   ├── POJOPropertyBuilder$1.class
│   │           │   ├── POJOPropertyBuilder$2.class
│   │           │   ├── POJOPropertyBuilder$3.class
│   │           │   ├── POJOPropertyBuilder$4.class
│   │           │   ├── POJOPropertyBuilder$5.class
│   │           │   ├── POJOPropertyBuilder$6.class
│   │           │   ├── POJOPropertyBuilder$Linked.class
│   │           │   ├── POJOPropertyBuilder$MemberIterator.class
│   │           │   ├── POJOPropertyBuilder$WithMember.class
│   │           │   ├── POJOPropertyBuilder.class
│   │           │   ├── PotentialCreator.class
│   │           │   ├── PotentialCreators.class
│   │           │   ├── SimpleMixInResolver.class
│   │           │   ├── TypeResolutionContext$Basic.class
│   │           │   ├── TypeResolutionContext$Empty.class
│   │           │   ├── TypeResolutionContext.class
│   │           │   ├── VirtualAnnotatedMember.class
│   │           │   ├── VisibilityChecker$1.class
│   │           │   ├── VisibilityChecker$Std.class
│   │           │   ├── VisibilityChecker.class
│   │           │   └── WithMember.class
│   │           ├── JavaType.class
│   │           ├── jdk14
│   │           │   ├── JDK14Util$CreatorLocator.class
│   │           │   ├── JDK14Util$RawTypeName.class
│   │           │   ├── JDK14Util$RecordAccessor.class
│   │           │   ├── JDK14Util.class
│   │           │   └── package-info.class
│   │           ├── json
│   │           │   ├── JsonMapper$Builder.class
│   │           │   └── JsonMapper.class
│   │           ├── JsonDeserializer$None.class
│   │           ├── JsonDeserializer.class
│   │           ├── jsonFormatVisitors
│   │           │   ├── JsonAnyFormatVisitor$Base.class
│   │           │   ├── JsonAnyFormatVisitor.class
│   │           │   ├── JsonArrayFormatVisitor$Base.class
│   │           │   ├── JsonArrayFormatVisitor.class
│   │           │   ├── JsonBooleanFormatVisitor$Base.class
│   │           │   ├── JsonBooleanFormatVisitor.class
│   │           │   ├── JsonFormatTypes.class
│   │           │   ├── JsonFormatVisitable.class
│   │           │   ├── JsonFormatVisitorWithSerializerProvider.class
│   │           │   ├── JsonFormatVisitorWrapper$Base.class
│   │           │   ├── JsonFormatVisitorWrapper.class
│   │           │   ├── JsonIntegerFormatVisitor$Base.class
│   │           │   ├── JsonIntegerFormatVisitor.class
│   │           │   ├── JsonMapFormatVisitor$Base.class
│   │           │   ├── JsonMapFormatVisitor.class
│   │           │   ├── JsonNullFormatVisitor$Base.class
│   │           │   ├── JsonNullFormatVisitor.class
│   │           │   ├── JsonNumberFormatVisitor$Base.class
│   │           │   ├── JsonNumberFormatVisitor.class
│   │           │   ├── JsonObjectFormatVisitor$Base.class
│   │           │   ├── JsonObjectFormatVisitor.class
│   │           │   ├── JsonStringFormatVisitor$Base.class
│   │           │   ├── JsonStringFormatVisitor.class
│   │           │   ├── JsonValueFormat.class
│   │           │   ├── JsonValueFormatVisitor$Base.class
│   │           │   ├── JsonValueFormatVisitor.class
│   │           │   └── package-info.class
│   │           ├── JsonMappingException$Reference.class
│   │           ├── JsonMappingException.class
│   │           ├── JsonNode$1.class
│   │           ├── JsonNode$OverwriteMode.class
│   │           ├── JsonNode.class
│   │           ├── jsonschema
│   │           │   ├── JsonSchema.class
│   │           │   ├── JsonSerializableSchema.class
│   │           │   ├── package-info.class
│   │           │   └── SchemaAware.class
│   │           ├── JsonSerializable$Base.class
│   │           ├── JsonSerializable.class
│   │           ├── JsonSerializer$None.class
│   │           ├── JsonSerializer.class
│   │           ├── jsontype
│   │           │   ├── BasicPolymorphicTypeValidator$Builder$1.class
│   │           │   ├── BasicPolymorphicTypeValidator$Builder$2.class
│   │           │   ├── BasicPolymorphicTypeValidator$Builder$3.class
│   │           │   ├── BasicPolymorphicTypeValidator$Builder$4.class
│   │           │   ├── BasicPolymorphicTypeValidator$Builder$5.class
│   │           │   ├── BasicPolymorphicTypeValidator$Builder$6.class
│   │           │   ├── BasicPolymorphicTypeValidator$Builder$7.class
│   │           │   ├── BasicPolymorphicTypeValidator$Builder.class
│   │           │   ├── BasicPolymorphicTypeValidator$NameMatcher.class
│   │           │   ├── BasicPolymorphicTypeValidator$TypeMatcher.class
│   │           │   ├── BasicPolymorphicTypeValidator.class
│   │           │   ├── DefaultBaseTypeLimitingValidator$UnsafeBaseTypes.class
│   │           │   ├── DefaultBaseTypeLimitingValidator.class
│   │           │   ├── impl
│   │           │   │   ├── AsArrayTypeDeserializer.class
│   │           │   │   ├── AsArrayTypeSerializer.class
│   │           │   │   ├── AsDeductionTypeDeserializer.class
│   │           │   │   ├── AsDeductionTypeSerializer.class
│   │           │   │   ├── AsExistingPropertyTypeSerializer.class
│   │           │   │   ├── AsExternalTypeDeserializer.class
│   │           │   │   ├── AsExternalTypeSerializer.class
│   │           │   │   ├── AsPropertyTypeDeserializer.class
│   │           │   │   ├── AsPropertyTypeSerializer.class
│   │           │   │   ├── AsWrapperTypeDeserializer.class
│   │           │   │   ├── AsWrapperTypeSerializer.class
│   │           │   │   ├── ClassNameIdResolver.class
│   │           │   │   ├── LaissezFaireSubTypeValidator.class
│   │           │   │   ├── MinimalClassNameIdResolver.class
│   │           │   │   ├── package-info.class
│   │           │   │   ├── SimpleNameIdResolver.class
│   │           │   │   ├── StdSubtypeResolver.class
│   │           │   │   ├── StdTypeResolverBuilder$1.class
│   │           │   │   ├── StdTypeResolverBuilder.class
│   │           │   │   ├── SubTypeValidator.class
│   │           │   │   ├── TypeDeserializerBase.class
│   │           │   │   ├── TypeIdResolverBase.class
│   │           │   │   ├── TypeNameIdResolver.class
│   │           │   │   └── TypeSerializerBase.class
│   │           │   ├── NamedType.class
│   │           │   ├── package-info.class
│   │           │   ├── PolymorphicTypeValidator$Base.class
│   │           │   ├── PolymorphicTypeValidator$Validity.class
│   │           │   ├── PolymorphicTypeValidator.class
│   │           │   ├── SubtypeResolver.class
│   │           │   ├── TypeDeserializer$1.class
│   │           │   ├── TypeDeserializer.class
│   │           │   ├── TypeIdResolver.class
│   │           │   ├── TypeResolverBuilder.class
│   │           │   ├── TypeSerializer$1.class
│   │           │   └── TypeSerializer.class
│   │           ├── KeyDeserializer$None.class
│   │           ├── KeyDeserializer.class
│   │           ├── MapperFeature.class
│   │           ├── MappingIterator.class
│   │           ├── MappingJsonFactory.class
│   │           ├── module
│   │           │   ├── package-info.class
│   │           │   ├── SimpleAbstractTypeResolver.class
│   │           │   ├── SimpleDeserializers.class
│   │           │   ├── SimpleKeyDeserializers.class
│   │           │   ├── SimpleModule.class
│   │           │   ├── SimpleSerializers.class
│   │           │   └── SimpleValueInstantiators.class
│   │           ├── Module$SetupContext.class
│   │           ├── Module.class
│   │           ├── node
│   │           │   ├── ArrayNode.class
│   │           │   ├── BaseJsonNode$1.class
│   │           │   ├── BaseJsonNode.class
│   │           │   ├── BigIntegerNode.class
│   │           │   ├── BinaryNode.class
│   │           │   ├── BooleanNode.class
│   │           │   ├── ContainerNode.class
│   │           │   ├── DecimalNode.class
│   │           │   ├── DoubleNode.class
│   │           │   ├── FloatNode.class
│   │           │   ├── InternalNodeMapper$IteratorStack.class
│   │           │   ├── InternalNodeMapper$WrapperForSerializer.class
│   │           │   ├── InternalNodeMapper.class
│   │           │   ├── IntNode.class
│   │           │   ├── JsonNodeCreator.class
│   │           │   ├── JsonNodeFactory.class
│   │           │   ├── JsonNodeType.class
│   │           │   ├── LongNode.class
│   │           │   ├── MissingNode.class
│   │           │   ├── NodeCursor$ArrayCursor.class
│   │           │   ├── NodeCursor$ObjectCursor.class
│   │           │   ├── NodeCursor$RootCursor.class
│   │           │   ├── NodeCursor.class
│   │           │   ├── NodeSerialization.class
│   │           │   ├── NullNode.class
│   │           │   ├── NumericNode.class
│   │           │   ├── ObjectNode.class
│   │           │   ├── package-info.class
│   │           │   ├── POJONode.class
│   │           │   ├── ShortNode.class
│   │           │   ├── TextNode.class
│   │           │   ├── TreeTraversingParser$1.class
│   │           │   ├── TreeTraversingParser.class
│   │           │   └── ValueNode.class
│   │           ├── ObjectMapper$1.class
│   │           ├── ObjectMapper$2.class
│   │           ├── ObjectMapper$3.class
│   │           ├── ObjectMapper$DefaultTypeResolverBuilder.class
│   │           ├── ObjectMapper$DefaultTyping.class
│   │           ├── ObjectMapper.class
│   │           ├── ObjectReader.class
│   │           ├── ObjectWriter$GeneratorSettings.class
│   │           ├── ObjectWriter$Prefetch.class
│   │           ├── ObjectWriter.class
│   │           ├── package-info.class
│   │           ├── PropertyMetadata$MergeInfo.class
│   │           ├── PropertyMetadata.class
│   │           ├── PropertyName.class
│   │           ├── PropertyNamingStrategies$KebabCaseStrategy.class
│   │           ├── PropertyNamingStrategies$LowerCamelCaseStrategy.class
│   │           ├── PropertyNamingStrategies$LowerCaseStrategy.class
│   │           ├── PropertyNamingStrategies$LowerDotCaseStrategy.class
│   │           ├── PropertyNamingStrategies$NamingBase.class
│   │           ├── PropertyNamingStrategies$SnakeCaseStrategy.class
│   │           ├── PropertyNamingStrategies$UpperCamelCaseStrategy.class
│   │           ├── PropertyNamingStrategies$UpperSnakeCaseStrategy.class
│   │           ├── PropertyNamingStrategies.class
│   │           ├── PropertyNamingStrategy$KebabCaseStrategy.class
│   │           ├── PropertyNamingStrategy$LowerCaseStrategy.class
│   │           ├── PropertyNamingStrategy$LowerDotCaseStrategy.class
│   │           ├── PropertyNamingStrategy$PropertyNamingStrategyBase.class
│   │           ├── PropertyNamingStrategy$SnakeCaseStrategy.class
│   │           ├── PropertyNamingStrategy$UpperCamelCaseStrategy.class
│   │           ├── PropertyNamingStrategy.class
│   │           ├── RuntimeJsonMappingException.class
│   │           ├── SequenceWriter.class
│   │           ├── ser
│   │           │   ├── AnyGetterWriter.class
│   │           │   ├── BasicSerializerFactory$1.class
│   │           │   ├── BasicSerializerFactory.class
│   │           │   ├── BeanPropertyFilter.class
│   │           │   ├── BeanPropertyWriter.class
│   │           │   ├── BeanSerializerBuilder.class
│   │           │   ├── BeanSerializer.class
│   │           │   ├── BeanSerializerFactory.class
│   │           │   ├── BeanSerializerModifier.class
│   │           │   ├── ContainerSerializer.class
│   │           │   ├── ContextualSerializer.class
│   │           │   ├── DefaultSerializerProvider$Impl.class
│   │           │   ├── DefaultSerializerProvider.class
│   │           │   ├── FilterProvider.class
│   │           │   ├── impl
│   │           │   │   ├── AttributePropertyWriter.class
│   │           │   │   ├── BeanAsArraySerializer.class
│   │           │   │   ├── FailingSerializer.class
│   │           │   │   ├── FilteredBeanPropertyWriter$MultiView.class
│   │           │   │   ├── FilteredBeanPropertyWriter$SingleView.class
│   │           │   │   ├── FilteredBeanPropertyWriter.class
│   │           │   │   ├── IndexedListSerializer.class
│   │           │   │   ├── IndexedStringListSerializer.class
│   │           │   │   ├── IteratorSerializer.class
│   │           │   │   ├── MapEntrySerializer$1.class
│   │           │   │   ├── MapEntrySerializer.class
│   │           │   │   ├── ObjectIdWriter.class
│   │           │   │   ├── package-info.class
│   │           │   │   ├── PropertyBasedObjectIdGenerator.class
│   │           │   │   ├── PropertySerializerMap$Double.class
│   │           │   │   ├── PropertySerializerMap$Empty.class
│   │           │   │   ├── PropertySerializerMap$Multi.class
│   │           │   │   ├── PropertySerializerMap$SerializerAndMapResult.class
│   │           │   │   ├── PropertySerializerMap$Single.class
│   │           │   │   ├── PropertySerializerMap$TypeAndSerializer.class
│   │           │   │   ├── PropertySerializerMap.class
│   │           │   │   ├── ReadOnlyClassToSerializerMap$Bucket.class
│   │           │   │   ├── ReadOnlyClassToSerializerMap.class
│   │           │   │   ├── SimpleBeanPropertyFilter$1.class
│   │           │   │   ├── SimpleBeanPropertyFilter$FilterExceptFilter.class
│   │           │   │   ├── SimpleBeanPropertyFilter$SerializeExceptFilter.class
│   │           │   │   ├── SimpleBeanPropertyFilter.class
│   │           │   │   ├── SimpleFilterProvider.class
│   │           │   │   ├── StringArraySerializer.class
│   │           │   │   ├── StringCollectionSerializer.class
│   │           │   │   ├── TypeWrappedSerializer.class
│   │           │   │   ├── UnknownSerializer.class
│   │           │   │   ├── UnsupportedTypeSerializer.class
│   │           │   │   ├── UnwrappingBeanPropertyWriter$1.class
│   │           │   │   ├── UnwrappingBeanPropertyWriter.class
│   │           │   │   ├── UnwrappingBeanSerializer.class
│   │           │   │   └── WritableObjectId.class
│   │           │   ├── package-info.class
│   │           │   ├── PropertyBuilder$1.class
│   │           │   ├── PropertyBuilder.class
│   │           │   ├── PropertyFilter.class
│   │           │   ├── PropertyWriter.class
│   │           │   ├── ResolvableSerializer.class
│   │           │   ├── SerializerCache.class
│   │           │   ├── SerializerFactory.class
│   │           │   ├── Serializers$Base.class
│   │           │   ├── Serializers.class
│   │           │   ├── std
│   │           │   │   ├── ArraySerializerBase.class
│   │           │   │   ├── AsArraySerializerBase.class
│   │           │   │   ├── AtomicReferenceSerializer.class
│   │           │   │   ├── BeanSerializerBase$1.class
│   │           │   │   ├── BeanSerializerBase.class
│   │           │   │   ├── BooleanSerializer$AsNumber.class
│   │           │   │   ├── BooleanSerializer.class
│   │           │   │   ├── ByteArraySerializer.class
│   │           │   │   ├── ByteBufferSerializer.class
│   │           │   │   ├── CalendarSerializer.class
│   │           │   │   ├── ClassSerializer.class
│   │           │   │   ├── CollectionSerializer.class
│   │           │   │   ├── DateSerializer.class
│   │           │   │   ├── DateTimeSerializerBase.class
│   │           │   │   ├── EnumSerializer.class
│   │           │   │   ├── EnumSetSerializer.class
│   │           │   │   ├── FileSerializer.class
│   │           │   │   ├── InetAddressSerializer.class
│   │           │   │   ├── InetSocketAddressSerializer.class
│   │           │   │   ├── IterableSerializer.class
│   │           │   │   ├── JsonValueSerializer$TypeSerializerRerouter.class
│   │           │   │   ├── JsonValueSerializer.class
│   │           │   │   ├── MapProperty.class
│   │           │   │   ├── MapSerializer$1.class
│   │           │   │   ├── MapSerializer.class
│   │           │   │   ├── NonTypedScalarSerializerBase.class
│   │           │   │   ├── NullSerializer.class
│   │           │   │   ├── NumberSerializer$1.class
│   │           │   │   ├── NumberSerializer$BigDecimalAsStringSerializer.class
│   │           │   │   ├── NumberSerializer.class
│   │           │   │   ├── NumberSerializers$1.class
│   │           │   │   ├── NumberSerializers$Base.class
│   │           │   │   ├── NumberSerializers$DoubleSerializer.class
│   │           │   │   ├── NumberSerializers$FloatSerializer.class
│   │           │   │   ├── NumberSerializers$IntegerSerializer.class
│   │           │   │   ├── NumberSerializers$IntLikeSerializer.class
│   │           │   │   ├── NumberSerializers$LongSerializer.class
│   │           │   │   ├── NumberSerializers$ShortSerializer.class
│   │           │   │   ├── NumberSerializers.class
│   │           │   │   ├── ObjectArraySerializer.class
│   │           │   │   ├── RawSerializer.class
│   │           │   │   ├── ReferenceTypeSerializer$1.class
│   │           │   │   ├── ReferenceTypeSerializer.class
│   │           │   │   ├── SerializableSerializer.class
│   │           │   │   ├── SqlDateSerializer.class
│   │           │   │   ├── SqlTimeSerializer.class
│   │           │   │   ├── StaticListSerializerBase.class
│   │           │   │   ├── StdArraySerializers$BooleanArraySerializer.class
│   │           │   │   ├── StdArraySerializers$CharArraySerializer.class
│   │           │   │   ├── StdArraySerializers$DoubleArraySerializer.class
│   │           │   │   ├── StdArraySerializers$FloatArraySerializer.class
│   │           │   │   ├── StdArraySerializers$IntArraySerializer.class
│   │           │   │   ├── StdArraySerializers$LongArraySerializer.class
│   │           │   │   ├── StdArraySerializers$ShortArraySerializer.class
│   │           │   │   ├── StdArraySerializers$TypedPrimitiveArraySerializer.class
│   │           │   │   ├── StdArraySerializers.class
│   │           │   │   ├── StdDelegatingSerializer.class
│   │           │   │   ├── StdJdkSerializers$AtomicBooleanSerializer.class
│   │           │   │   ├── StdJdkSerializers$AtomicIntegerSerializer.class
│   │           │   │   ├── StdJdkSerializers$AtomicLongSerializer.class
│   │           │   │   ├── StdJdkSerializers.class
│   │           │   │   ├── StdKeySerializer.class
│   │           │   │   ├── StdKeySerializers$Default.class
│   │           │   │   ├── StdKeySerializers$Dynamic.class
│   │           │   │   ├── StdKeySerializers$EnumKeySerializer.class
│   │           │   │   ├── StdKeySerializers$StringKeySerializer.class
│   │           │   │   ├── StdKeySerializers.class
│   │           │   │   ├── StdScalarSerializer.class
│   │           │   │   ├── StdSerializer.class
│   │           │   │   ├── StringSerializer.class
│   │           │   │   ├── TimeZoneSerializer.class
│   │           │   │   ├── ToEmptyObjectSerializer.class
│   │           │   │   ├── TokenBufferSerializer.class
│   │           │   │   ├── ToStringSerializerBase.class
│   │           │   │   ├── ToStringSerializer.class
│   │           │   │   └── UUIDSerializer.class
│   │           │   └── VirtualBeanPropertyWriter.class
│   │           ├── SerializationConfig.class
│   │           ├── SerializationFeature.class
│   │           ├── SerializerProvider.class
│   │           ├── type
│   │           │   ├── ArrayType.class
│   │           │   ├── ClassKey.class
│   │           │   ├── ClassStack.class
│   │           │   ├── CollectionLikeType.class
│   │           │   ├── CollectionType.class
│   │           │   ├── IdentityEqualityType.class
│   │           │   ├── IterationType.class
│   │           │   ├── LogicalType.class
│   │           │   ├── MapLikeType.class
│   │           │   ├── MapType.class
│   │           │   ├── package-info.class
│   │           │   ├── PlaceholderForType.class
│   │           │   ├── ReferenceType.class
│   │           │   ├── ResolvedRecursiveType.class
│   │           │   ├── SimpleType.class
│   │           │   ├── TypeBase.class
│   │           │   ├── TypeBindings$AsKey.class
│   │           │   ├── TypeBindings$TypeParamStash.class
│   │           │   ├── TypeBindings.class
│   │           │   ├── TypeFactory.class
│   │           │   ├── TypeModifier.class
│   │           │   ├── TypeParser$MyTokenizer.class
│   │           │   └── TypeParser.class
│   │           └── util
│   │               ├── AccessPattern.class
│   │               ├── Annotations.class
│   │               ├── ArrayBuilders$1.class
│   │               ├── ArrayBuilders$BooleanBuilder.class
│   │               ├── ArrayBuilders$ByteBuilder.class
│   │               ├── ArrayBuilders$DoubleBuilder.class
│   │               ├── ArrayBuilders$FloatBuilder.class
│   │               ├── ArrayBuilders$IntBuilder.class
│   │               ├── ArrayBuilders$LongBuilder.class
│   │               ├── ArrayBuilders$ShortBuilder.class
│   │               ├── ArrayBuilders.class
│   │               ├── ArrayIterator.class
│   │               ├── BeanUtil.class
│   │               ├── ByteBufferBackedInputStream.class
│   │               ├── ByteBufferBackedOutputStream.class
│   │               ├── ClassUtil$Ctor.class
│   │               ├── ClassUtil$EnumTypeLocator.class
│   │               ├── ClassUtil.class
│   │               ├── CompactStringObjectMap.class
│   │               ├── Converter$None.class
│   │               ├── Converter.class
│   │               ├── EnumResolver.class
│   │               ├── EnumValues.class
│   │               ├── ExceptionUtil.class
│   │               ├── IgnorePropertiesUtil$Checker.class
│   │               ├── IgnorePropertiesUtil.class
│   │               ├── internal
│   │               │   ├── Linked.class
│   │               │   ├── LinkedDeque$1.class
│   │               │   ├── LinkedDeque$2.class
│   │               │   ├── LinkedDeque$AbstractLinkedIterator.class
│   │               │   ├── LinkedDeque.class
│   │               │   ├── package-info.class
│   │               │   ├── PrivateMaxEntriesMap$1.class
│   │               │   ├── PrivateMaxEntriesMap$AddTask.class
│   │               │   ├── PrivateMaxEntriesMap$Builder.class
│   │               │   ├── PrivateMaxEntriesMap$DrainStatus$1.class
│   │               │   ├── PrivateMaxEntriesMap$DrainStatus$2.class
│   │               │   ├── PrivateMaxEntriesMap$DrainStatus$3.class
│   │               │   ├── PrivateMaxEntriesMap$DrainStatus.class
│   │               │   ├── PrivateMaxEntriesMap$EntryIterator.class
│   │               │   ├── PrivateMaxEntriesMap$EntrySet.class
│   │               │   ├── PrivateMaxEntriesMap$KeyIterator.class
│   │               │   ├── PrivateMaxEntriesMap$KeySet.class
│   │               │   ├── PrivateMaxEntriesMap$Node.class
│   │               │   ├── PrivateMaxEntriesMap$RemovalTask.class
│   │               │   ├── PrivateMaxEntriesMap$SerializationProxy.class
│   │               │   ├── PrivateMaxEntriesMap$UpdateTask.class
│   │               │   ├── PrivateMaxEntriesMap$ValueIterator.class
│   │               │   ├── PrivateMaxEntriesMap$Values.class
│   │               │   ├── PrivateMaxEntriesMap$WeightedValue.class
│   │               │   ├── PrivateMaxEntriesMap$WriteThroughEntry.class
│   │               │   └── PrivateMaxEntriesMap.class
│   │               ├── ISO8601DateFormat.class
│   │               ├── ISO8601Utils.class
│   │               ├── JacksonCollectors.class
│   │               ├── JSONPObject.class
│   │               ├── JSONWrappedObject.class
│   │               ├── LinkedNode.class
│   │               ├── LookupCache.class
│   │               ├── LRUMap.class
│   │               ├── Named.class
│   │               ├── NameTransformer$1.class
│   │               ├── NameTransformer$2.class
│   │               ├── NameTransformer$3.class
│   │               ├── NameTransformer$Chained.class
│   │               ├── NameTransformer$NopTransformer.class
│   │               ├── NameTransformer.class
│   │               ├── NativeImageUtil.class
│   │               ├── ObjectBuffer.class
│   │               ├── package-info.class
│   │               ├── PrimitiveArrayBuilder$Node.class
│   │               ├── PrimitiveArrayBuilder.class
│   │               ├── RawValue.class
│   │               ├── RootNameLookup.class
│   │               ├── SimpleBeanPropertyDefinition.class
│   │               ├── StdConverter.class
│   │               ├── StdDateFormat.class
│   │               ├── TokenBuffer$1.class
│   │               ├── TokenBuffer$Parser.class
│   │               ├── TokenBuffer$Segment.class
│   │               ├── TokenBuffer.class
│   │               ├── TokenBufferReadContext.class
│   │               ├── TypeKey.class
│   │               ├── ViewMatcher$Multi.class
│   │               ├── ViewMatcher$Single.class
│   │               └── ViewMatcher.class
│   └── google
│       └── cloud
│           └── functions
│               ├── BackgroundFunction.class
│               ├── CloudEventsFunction.class
│               ├── Context.class
│               ├── HttpFunction.class
│               ├── HttpMessage.class
│               ├── HttpRequest$HttpPart.class
│               ├── HttpRequest.class
│               ├── HttpResponse.class
│               ├── RawBackgroundFunction.class
│               ├── TypedFunction$WireFormat.class
│               └── TypedFunction.class
├── io
│   ├── cloudevents
│   │   ├── CloudEventAttributes.class
│   │   ├── CloudEvent.class
│   │   ├── CloudEventContext.class
│   │   ├── CloudEventData.class
│   │   ├── CloudEventExtension.class
│   │   ├── CloudEventExtensions.class
│   │   ├── lang
│   │   │   └── Nullable.class
│   │   ├── rw
│   │   │   ├── CloudEventContextReader.class
│   │   │   ├── CloudEventContextWriter.class
│   │   │   ├── CloudEventDataMapper.class
│   │   │   ├── CloudEventReader.class
│   │   │   ├── CloudEventRWException$CloudEventRWExceptionKind.class
│   │   │   ├── CloudEventRWException.class
│   │   │   ├── CloudEventWriter.class
│   │   │   └── CloudEventWriterFactory.class
│   │   ├── SpecVersion.class
│   │   └── types
│   │       └── Time.class
│   └── github
│       └── renepanke
│           └── faasal
│               ├── api
│               │   ├── Function.class
│               │   ├── Logger.class
│               │   └── Result.class
│               └── gcp
│                   ├── GcpFunctionDecorator$1.class
│                   ├── GcpFunctionDecorator.class
│                   └── GcpFunctionsLogger.class
└── META-INF
    ├── FastDoubleParser-LICENSE
    ├── FastDoubleParser-NOTICE
    ├── LICENSE
    ├── MANIFEST.MF
    ├── maven
    │   ├── com.fasterxml.jackson.core
    │   │   ├── jackson-annotations
    │   │   │   ├── pom.properties
    │   │   │   └── pom.xml
    │   │   ├── jackson-core
    │   │   │   ├── pom.properties
    │   │   │   └── pom.xml
    │   │   └── jackson-databind
    │   │       ├── pom.properties
    │   │       └── pom.xml
    │   ├── com.google.cloud.functions
    │   │   └── functions-framework-api
    │   │       ├── pom.properties
    │   │       └── pom.xml
    │   └── io.cloudevents
    │       └── cloudevents-api
    │           ├── pom.properties
    │           └── pom.xml
    ├── NOTICE
    ├── services
    │   ├── com.fasterxml.jackson.core.JsonFactory
    │   └── com.fasterxml.jackson.core.ObjectCodec
    ├── thirdparty-LICENSE
    └── versions
        ├── 11
        │   └── com
        │       └── fasterxml
        │           └── jackson
        │               └── core
        │                   └── internal
        │                       └── shaded
        │                           └── fdp
        │                               └── v2_18_0
        │                                   ├── BigSignificand.class
        │                                   ├── FastDoubleSwar.class
        │                                   └── FastIntegerMath.class
        ├── 17
        │   └── com
        │       └── fasterxml
        │           └── jackson
        │               └── core
        │                   └── internal
        │                       └── shaded
        │                           └── fdp
        │                               └── v2_18_0
        │                                   ├── FastDoubleSwar.class
        │                                   └── FastIntegerMath.class
        ├── 21
        │   └── com
        │       └── fasterxml
        │           └── jackson
        │               └── core
        │                   └── internal
        │                       └── shaded
        │                           └── fdp
        │                               └── v2_18_0
        │                                   ├── FastDoubleSwar.class
        │                                   └── FastIntegerMath.class
        ├── 22
        │   └── com
        │       └── fasterxml
        │           └── jackson
        │               └── core
        │                   └── internal
        │                       └── shaded
        │                           └── fdp
        │                               └── v2_18_0
        │                                   ├── FastDoubleSwar.class
        │                                   └── FastIntegerMath.class
        └── 9
            └── module-info.class
```
