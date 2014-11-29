package com.wolffangx.dvdlibrary.tests;

import com.wolffangx.dvdlibrary.tests.individual.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * A Test Suite with ALL SERVICE TESTS
 *
 * @author borislav.draganov
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({AudioServiceTest.class, DVDServiceTest.class, GenreServiceTest.class, LanguageServiceTest.class, MovieServiceTest.class, RatingServiceTest.class})
public class AllTests {
}