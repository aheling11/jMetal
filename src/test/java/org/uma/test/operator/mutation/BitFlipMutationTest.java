//  PolynomialMutationTest.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//
//  Copyright (c) 2014 Antonio J. Nebro
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
//
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package org.uma.test.operator.mutation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal.core.Problem;
import org.uma.jmetal.core.Solution;
import org.uma.jmetal.operator.mutation.BitFlipMutation;
import org.uma.jmetal.problem.Kursawe;
import org.uma.jmetal.util.JMetalException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Antonio J. Nebro on 21/04/14.
 */
public class BitFlipMutationTest {
  BitFlipMutation mutation_ ;
  Problem problem_ ;

  static final double DELTA = 0.0000000000001 ;

  @Before
  public void setUp() throws JMetalException {
    problem_ = new Kursawe("Real", 3) ;

    mutation_ = new BitFlipMutation.Builder().
      probability(1.0 / problem_.getNumberOfVariables())
      .build() ;
  }

  @After
  public void tearDown() throws Exception {
    mutation_ = null ;
    problem_ = null ;
  }

  @Test
  public void defaultParametersTest() {
    assertEquals(1.0/problem_.getNumberOfVariables(), mutation_.getMutationProbability(), DELTA) ;
  }

  @Test
  public void setMutationProbabilityTest() {
    mutation_ = new BitFlipMutation
      .Builder()
      .probability(0.02).build() ;

    assertEquals(0.02, mutation_.getMutationProbability(), DELTA) ;
  }

  @Test
  public void operatorExecutionTest() throws ClassNotFoundException {
    mutation_ = new BitFlipMutation.Builder()
      .probability(0.9)
      .build();

    Object result = mutation_.execute(new Solution(problem_));
    assertNotNull(result);
  }
}