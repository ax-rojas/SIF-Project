# SIF Application (NCN20)

Station Information File (SIF) JSON generator for the NOAA/NGS CORS network.  
Produces the `ncn20_SIF` format from MMT and NSRSDB PostgreSQL tables.

---

## Project Structure

```
sif_project/
├── pom.xml                                     Maven build file
├── README.md                                   This file
├── lib/                                        Place NGS internal JARs here
│   ├── ngs-ldap.jar                            (LdapAuthenticator, LdapException)
│   └── ngs-db.jar                              (DBUtil)
└── src/main/
    ├── java/
    │   ├── sifapplication/
    │   │   ├── SIFapplication.java              Main entry point + JSON builder
    │   │   └── Validation.java                  JSON validation utilities
    │   ├── DB/
    │   │   ├── LogDB_PG.java                    Database access layer (updated for MMT/NCN)
    │   │   └── DBconnection.java                PostgreSQL connection manager
    │   └── model/
    │       ├── Sec1_Attr.java                   Site Identification attributes
    │       ├── Sec2_Attr.java                   Site Location attributes
    │       ├── Sec3_Attr.java                   GNSS Receiver attributes
    │       ├── Sec4_Attr.java                   GNSS Antenna attributes
    │       ├── SIF_Attr.java                    SIF aggregate (Oracle)
    │       ├── SIF_Attr_PG.java                 SIF aggregate (Postgres)
    │       ├── OPT_Attr.java                    Ocean Pole Tide attributes
    │       ├── OTL_Attr.java                    Ocean Tide Loading attributes
    │       ├── OTL_Sec_Attr.java                OTL tidal constituent attributes
    │       ├── Station_Attr.java                Station metadata attributes
    │       ├── Occupation_Attr.java             Position/occupation segment attributes
    │       ├── Pid_Attr.java                    PID attributes
    │       ├── CORS_Attr.java                   CORS aggregate
    │       └── Sec_Obj.java                     Generic label/value pair
    └── resources/DB/
        ├── sif_metadata.json                    Metadata template (NCN20/ITRF2020)
        └── DBConnectionPostgres.properties      Database connection properties
```

---

## Database Tables Queried

| JSON Block              | Source Table                      | Key Column(s)                           |
|-------------------------|-----------------------------------|-----------------------------------------|
| `ID`                    | `mmt.slm_sitealias`              | `site_name` (9-char RINEX3 ID)          |
| `DOMES`                 | `mmt.slm_siteidentification`     | `iers_domes_number`                     |
| `DESCRIPTION`           | `mmt.slm_siteidentification`     | `site_name`                             |
| `APPROXIMATE_COORDINATES`| `nsrsdb.ncn_cf_seg`              | Latest segment `coord->'COORDINATES'`   |
| `OCEAN_TIDE_LOADING`    | `nsrsdb.cors_stations`           | `ocean_tide` (FES2014B)                 |
| `OCEAN_POLE_TIDE`       | `mmt.slm_corslog`               | `ocean_tide->'OCEAN_POLE_TIDE'`         |
| `ANTENNA[]`             | `mmt.slm_siteantenna`           | Joined with `slm_antenna` + `slm_radome`|
| `RECEIVER[]`            | `mmt.slm_sitereceiver`          | Joined with `mmt.slm_receiver`          |
| `OCCUPATION[]`          | `nsrsdb.ncn_cf_seg`             | `coord` JSON (passthrough)              |
| `NONLINEAR_TERMS`       | `nsrsdb.ncn_seasonal_terms`     | `seasonal_terms` JSON                   |
| `PSD_TERMS`             | `nsrsdb.ncn_psd_terms`          | `psd` JSON (when present)               |

---

## Prerequisites

- **Java 11+**
- **Apache Maven 3.6+**
- **PostgreSQL access** to the NGS MMT and NSRSDB databases
- **NGS internal JAR files** (not on Maven Central):
  - `ngs-ldap.jar` — provides `gov.noaa.ngs.ldap.LdapAuthenticator`, `LdapException`
  - `ngs-db.jar` — provides `gov.noaa.ngs.db.DBUtil`

---

## Setup

### 1. Install NGS Internal Libraries

Place the NGS JAR files in the `lib/` directory, then register them with Maven:

```bash
mvn install:install-file \
    -Dfile=lib/ngs-ldap.jar \
    -DgroupId=gov.noaa.ngs \
    -DartifactId=ngs-ldap \
    -Dversion=1.0 \
    -Dpackaging=jar

mvn install:install-file \
    -Dfile=lib/ngs-db.jar \
    -DgroupId=gov.noaa.ngs \
    -DartifactId=ngs-db \
    -Dversion=1.0 \
    -Dpackaging=jar
```

### 2. Configure Database Connection

Edit `src/main/resources/DB/DBConnectionPostgres.properties` to point to your PostgreSQL instance:

```properties
NGS_SERVERENV=dev
vendor=postgres
postgres.driver=jdbc:postgresql
dev.server=your-pg-host
dev.port=5432
dev.db=your-database
```

### 3. Build

```bash
mvn clean package
```

This produces a fat JAR at `target/sifapplication-2.0-NCN20.jar`.

---

## Usage

### Run a single station:

```bash
java -jar target/sifapplication-2.0-NCN20.jar 0 1LSU00USA
```

### Run multiple stations:

```bash
java -jar target/sifapplication-2.0-NCN20.jar 0 1LSU00USA AC0800USA AC0900USA
```

### Run all stations (from database):

```bash
java -jar target/sifapplication-2.0-NCN20.jar 0
```

### Arguments:

| Arg       | Description                                          |
|-----------|------------------------------------------------------|
| `0`       | All stations with a log keeper in `mmt.slm_corslog`  |
| `1`       | NGS-kept stations only (log_keeper != 'IGSNET')      |
| `2`       | IGSNET-kept stations only                            |
| Station IDs | 9-character RINEX3 IDs (e.g. `1LSU00USA`)         |

### Output:

Creates `SIF_<forWhich>_<timestamp>.json` in the current directory.

---

## Output JSON Format (ncn20_SIF)

```json
{
    "METADATA": {
        "FORMAT_VERSION": 1,
        "REFERENCE_FRAME": "ITRF2020",
        "REFERENCE_EPOCH": 2020,
        "OTL_MODEL": "FES2014B",
        ...
    },
    "STATIONS": [
        {
            "ID": "1LSU00USA",
            "DOMES": "",
            "DESCRIPTION": "LOUISIANA STATE U",
            "APPROXIMATE_COORDINATES": [-113403.026, -5504361.306, 3209404.176],
            "OCEAN_TIDE_LOADING": { "COMMENT": [...], "M2": {...}, ... "SSA": {...} },
            "OCEAN_POLE_TIDE": { "COMMENT": "...", "NORTH_REAL": ..., ... },
            "ANTENNA": [ { "START", "END", "TYPE", "SERIAL_NUMBER", "ARP_MINUS_GRP_ENU", "ORIENTATION", "DO_NOT_USE", "SOURCE" } ],
            "RECEIVER": [ { "START", "END", "TYPE", "SERIAL_NUMBER", "FIRMWARE", "SATELLITE_SYSTEMS", "DO_NOT_USE", "SOURCE" } ],
            "OCCUPATION": [ { "START", "END", "REFERENCE_FRAME", "REFERENCE_EPOCH", "COORDINATES", "COORDINATE_SIGMAS", "VELOCITY", "VELOCITY_SIGMAS", "6X6_COVARIANCE_MATRIX", "DO_NOT_USE", "SOURCE" } ],
            "NONLINEAR_TERMS": { "PERIODIC_TERMS": [ { "START", "END", "COMMENT", "SOURCE", "FREQUENCIES": [...] } ] },
            "PSD_TERMS": [ ... ]  // only for stations with post-seismic deformation
        }
    ]
}
```
